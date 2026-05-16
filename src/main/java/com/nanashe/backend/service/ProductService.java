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
                .map(p -> new ProductResponseDto(
                        p.getId(),
                        p.getName(),
                        p.getCategory().getName(),
                        p.getOrigin().getName(),
                        p.getAliases().stream().map(a -> a.getName()).toList(),
                        p.getAlternatives().stream()
                                .map(alt -> new AlternativeResponseDto(
                                        alt.getName(),
                                        alt.getDescription(),
                                        alt.getUrl(),
                                        alt.getOrigin().getName()
                                ))
                                .toList()
                ))
                .toList();
    }
}
