package com.nanashe.backend.client;

import com.nanashe.backend.dto.alternatives.request.AiGenerateRequestDto;
import com.nanashe.backend.dto.alternatives.response.AiAlternativeSearchResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ai-service", url = "${ai.service.url}")
public interface AiServiceClient {

    @PostMapping("/api/v1/generate")
    AiAlternativeSearchResponseDto generateAlternatives(@RequestBody AiGenerateRequestDto request);
}
