package com.nanashe.backend.client;

import com.nanashe.backend.dto.AiAlternativeDto;
import com.nanashe.backend.dto.AiGenerateRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "ai-service", url = "${ai.service.url}")
public interface AiServiceClient {

    @PostMapping("/api/v1/generate")
    List<AiAlternativeDto> generateAlternatives(@RequestBody AiGenerateRequestDto request);
}
