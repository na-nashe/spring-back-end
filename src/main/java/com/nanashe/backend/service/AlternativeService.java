package com.nanashe.backend.service;

import com.nanashe.backend.client.AiServiceClient;
import com.nanashe.backend.dto.alternatives.request.AiGenerateRequestDto;
import com.nanashe.backend.dto.alternatives.request.AiGenerateSearchRequestDto;
import com.nanashe.backend.dto.alternatives.response.AiAlternativeSearchResponseDto;
import com.nanashe.backend.dto.alternatives.response.AlternativeSummaryResponseDto;
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

    public AlternativeSummaryResponseDto getCount() {
        return new AlternativeSummaryResponseDto(alternativeRepository.count());
    }

    public AiAlternativeSearchResponseDto generateAlternatives(AiGenerateSearchRequestDto request) {
        List<String> categories = categoryRepository.findByChildrenIsEmpty().stream()
                .map(Category::getName)
                .toList();
        return aiServiceClient.generateAlternatives(new AiGenerateRequestDto(request.productName(), categories));
    }
}
