package com.nanashe.backend.service;

import com.nanashe.backend.dto.categories.response.CategoryResponseDto;
import com.nanashe.backend.entity.Category;
import com.nanashe.backend.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryResponseDto> getMainCategories() {
        return categoryRepository.findByParentIsNull().stream()
                .map(this::toDto)
                .toList();
    }

    private CategoryResponseDto toDto(Category c) {
        List<CategoryResponseDto> children = c.getChildren() == null
                ? List.of()
                : c.getChildren().stream().map(this::toDto).toList();
        return new CategoryResponseDto(c.getId(), c.getName(), c.getIcon(), children);
    }
}
