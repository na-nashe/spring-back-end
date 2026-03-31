package com.nanashe.backend.service;

import com.nanashe.backend.dto.CategoryDTO;
import com.nanashe.backend.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryDTO> getMainCategories() {
        return categoryRepository.findByParentIsNull().stream()
                .map(c -> new CategoryDTO(c.getName(), c.getIcon()))
                .toList();
    }
}
