package com.nanashe.backend.service;

import com.nanashe.backend.dto.CategoryDto;
import com.nanashe.backend.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryDto> getMainCategories() {
        return categoryRepository.findByParentIsNull().stream()
                .map(c -> new CategoryDto(c.getName(), c.getIcon()))
                .toList();
    }
}
