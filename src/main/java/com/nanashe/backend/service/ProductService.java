package com.nanashe.backend.service;

import com.nanashe.backend.dto.alternatives.response.AlternativeResponseDto;
import com.nanashe.backend.dto.alternatives.response.ProductResponseDto;
import com.nanashe.backend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductResponseDto> getProducts() {
        return productRepository.findAll().stream()
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
