package com.nanashe.backend.repository;

import com.nanashe.backend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    Optional<Product> findFirstByAliasesNameIn(List<String> names);
}
