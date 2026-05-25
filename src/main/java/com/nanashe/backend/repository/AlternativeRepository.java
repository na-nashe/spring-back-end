package com.nanashe.backend.repository;

import com.nanashe.backend.entity.Alternative;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AlternativeRepository extends JpaRepository<Alternative, Integer> {

    List<Alternative> findByIsCashbackAvailableTrue();

    List<Alternative> findByIsCashbackAvailableTrueAndNameContainingIgnoreCase(String name);

    @Query(value = """
            SELECT alt_name
            FROM unnest(:alternatives) AS alt_name
            WHERE NOT EXISTS (
                SELECT 1 FROM alternatives a
                WHERE lower(a.name) = lower(alt_name)
            )
            """, nativeQuery = true)
    List<String> findNewAlternativeNames(@Param("alternatives") String[] alternatives);
}
