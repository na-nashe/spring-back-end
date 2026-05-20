package com.nanashe.backend.service;

import com.nanashe.backend.client.AiServiceClient;
import com.nanashe.backend.dto.alternatives.request.AiGenerateRequestDto;
import com.nanashe.backend.dto.alternatives.request.SearchRequestDto;
import com.nanashe.backend.dto.alternatives.response.AlternativeResponseDto;
import com.nanashe.backend.dto.alternatives.response.AlternativeSearchResponseDto;
import com.nanashe.backend.dto.alternatives.response.AlternativeSummaryResponseDto;
import com.nanashe.backend.entity.Alternative;
import com.nanashe.backend.entity.Category;
import com.nanashe.backend.entity.Product;
import com.nanashe.backend.entity.enums.PricingModel;
import com.nanashe.backend.repository.AlternativeRepository;
import com.nanashe.backend.repository.CategoryRepository;
import com.nanashe.backend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlternativeService {

    private final AlternativeRepository alternativeRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final AiServiceClient aiServiceClient;

    public AlternativeSummaryResponseDto getCount() {
        return new AlternativeSummaryResponseDto(alternativeRepository.count());
    }

    @Transactional(readOnly = true)
    public AlternativeSearchResponseDto findAlternatives(SearchRequestDto request) {
        return productRepository.findByAliasesName(request.productName())
                .filter(Product::hasAlternatives)
                .map(this::getAlternativesFromProduct)
                .orElseGet(() -> getAlternativesFromAIService(request));
    }

    private AlternativeSearchResponseDto getAlternativesFromAIService(SearchRequestDto request) {
        List<String> categories = categoryRepository.findByChildrenIsEmpty().stream()
                .map(Category::getName)
                .toList();
        List<String> pricingModels = List.of(PricingModel.values()).stream()
                .map(Enum::name)
                .toList();
        return aiServiceClient.generateAlternatives(new AiGenerateRequestDto(request.productName(), categories, pricingModels));
    }

    private AlternativeSearchResponseDto getAlternativesFromProduct(Product product) {
        List<AlternativeResponseDto> cached = product.getAlternatives().stream()
                .map(this::toResponseDto)
                .toList();
        return new AlternativeSearchResponseDto("Знайшли альтернативи для Вас!", product.getName(), cached);
    }

    private AlternativeResponseDto toResponseDto(Alternative alt) {
        return new AlternativeResponseDto(
                alt.getName(),
                alt.getDescription(),
                alt.getUrl(),
                alt.getOrigin().getName(),
                alt.getPricingModel()
        );
    }
}
