package com.nanashe.backend.service;

import com.nanashe.backend.client.AiServiceClient;
import com.nanashe.backend.dto.AiAlternativeSearchResponseDto;
import com.nanashe.backend.dto.AiGenerateRequestDto;
import com.nanashe.backend.dto.AiGenerateSearchRequestDto;
import com.nanashe.backend.dto.AlternativeCountDto;
import com.nanashe.backend.entity.Category;
import com.nanashe.backend.repository.AlternativeRepository;
import com.nanashe.backend.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlternativeService {

    private final AlternativeRepository alternativeRepository;
    private final CategoryRepository categoryRepository;
    private final AiServiceClient aiServiceClient;

    public AlternativeCountDto getCount() {
        return new AlternativeCountDto(alternativeRepository.count());
    }

    public AiAlternativeSearchResponseDto generateAlternatives(AiGenerateSearchRequestDto request) {
        List<String> categories = categoryRepository.findByChildrenIsEmpty().stream()
                .map(Category::getName)
                .toList();
        return aiServiceClient.generateAlternatives(new AiGenerateRequestDto(request.productName(), categories));
    }
}
