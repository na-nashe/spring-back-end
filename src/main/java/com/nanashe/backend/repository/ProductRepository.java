package com.nanashe.backend.repository;

import com.nanashe.backend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByAliasesNameIn(List<String> names);
}
