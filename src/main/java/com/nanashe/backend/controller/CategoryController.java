package com.nanashe.backend.controller;

import com.nanashe.backend.dto.categories.response.CategoryResponseDto;
import com.nanashe.backend.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryResponseDto> getMainCategories() {
        return categoryService.getMainCategories();
    }
}
