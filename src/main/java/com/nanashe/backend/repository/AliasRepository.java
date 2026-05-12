package com.nanashe.backend.repository;

import com.nanashe.backend.entity.Alias;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AliasRepository extends JpaRepository<Alias, Integer> {
}
