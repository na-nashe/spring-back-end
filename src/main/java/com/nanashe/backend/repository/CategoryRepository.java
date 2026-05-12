package com.nanashe.backend.repository;

import com.nanashe.backend.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    List<Category> findByParentIsNull();

    List<Category> findByChildrenIsEmpty();

    Optional<Category> findByNameIgnoreCase(String name);
}
