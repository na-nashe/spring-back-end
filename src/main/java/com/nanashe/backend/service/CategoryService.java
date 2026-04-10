package com.nanashe.backend.service;

import com.nanashe.backend.dto.categories.response.CategoryResponseDto;
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
                .map(c -> new CategoryResponseDto(c.getName(), c.getIcon()))
                .toList();
    }
}
