package com.nanashe.backend.service;

import com.nanashe.backend.dto.alternatives.response.AlternativeResponseDto;
import com.nanashe.backend.dto.product.response.ProductResponseDto;
import com.nanashe.backend.entity.Product;
import com.nanashe.backend.repository.CategoryRepository;
import com.nanashe.backend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public List<ProductResponseDto> getProducts() {
        return toResponseDtos(productRepository.findAll());
    }

    public List<ProductResponseDto> getProductsByCategory(Integer categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");
        }
        return toResponseDtos(productRepository.findByCategoryId(categoryId));
    }

    private List<ProductResponseDto> toResponseDtos(List<Product> products) {
        return products.stream()
                .map(p -> ProductResponseDto.builder()
                        .id(p.getId())
                        .name(p.getName())
                        .category(p.getCategoryName())
                        .origin(p.getOriginName())
                        .aliases(p.getAliasesAsStrings())
                        .alternatives(p.getAlternatives().stream()
                                .map(alt -> AlternativeResponseDto.builder()
                                        .name(alt.getName())
                                        .description(alt.getDescription())
                                        .url(alt.getUrl())
                                        .country(alt.getOrigin().getName())
                                        .build())
                                .toList())
                        .build())
                .toList();
    }
}
