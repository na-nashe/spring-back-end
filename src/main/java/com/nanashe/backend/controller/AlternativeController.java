package com.nanashe.backend.controller;

import com.nanashe.backend.dto.alternatives.request.AiGenerateSearchRequestDto;
import com.nanashe.backend.dto.alternatives.response.AiAlternativeSearchResponseDto;
import com.nanashe.backend.dto.alternatives.response.AlternativeSummaryResponseDto;
import com.nanashe.backend.service.AlternativeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/alternatives")
@RequiredArgsConstructor
public class AlternativeController {

    private final AlternativeService alternativeService;

    @GetMapping("/summary")
    public AlternativeSummaryResponseDto getCount() {
        return alternativeService.getCount();
    }

    @PostMapping("/search")
    public AiAlternativeSearchResponseDto generateAlternatives(@RequestBody AiGenerateSearchRequestDto request) {
        return alternativeService.generateAlternatives(request);
    }
}
