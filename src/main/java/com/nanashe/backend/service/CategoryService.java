package com.nanashe.backend.service;

import com.nanashe.backend.dto.categories.response.CategoryResponseDto;
import com.nanashe.backend.entity.Category;
import com.nanashe.backend.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

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
        List<CategoryResponseDto> children = Optional.ofNullable(c.getChildren())
                .stream()
                .flatMap(Collection::stream)
                .map(this::toDto)
                .toList();

        return new CategoryResponseDto(c.getId(), c.getName(), c.getIcon(), children);
    }
}
