package com.nanashe.backend.service;

import com.nanashe.backend.client.AiServiceClient;
import com.nanashe.backend.dto.AiAlternativeDto;
import com.nanashe.backend.dto.AiGenerateRequestDto;
import com.nanashe.backend.dto.AlternativeCountDto;
import com.nanashe.backend.repository.AlternativeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlternativeService {

    private final AlternativeRepository alternativeRepository;
    private final AiServiceClient aiServiceClient;

    public AlternativeCountDto getCount() {
        return new AlternativeCountDto(alternativeRepository.count());
    }

    public List<AiAlternativeDto> generateAlternatives(AiGenerateRequestDto request) {
        return aiServiceClient.generateAlternatives(request);
    }
}
