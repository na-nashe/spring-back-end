package com.nanashe.backend.controller;

import com.nanashe.backend.dto.AiAlternativeSearchResponseDto;
import com.nanashe.backend.dto.AiGenerateSearchRequestDto;
import com.nanashe.backend.dto.AlternativeCountDto;
import com.nanashe.backend.service.AlternativeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/alternatives")
@RequiredArgsConstructor
public class AlternativeController {

    private final AlternativeService alternativeService;

    @GetMapping("/summary")
    public AlternativeCountDto getCount() {
        return alternativeService.getCount();
    }

    @PostMapping("/search")
    public AiAlternativeSearchResponseDto generateAlternatives(@RequestBody AiGenerateSearchRequestDto request) {
        return alternativeService.generateAlternatives(request);
    }
}
