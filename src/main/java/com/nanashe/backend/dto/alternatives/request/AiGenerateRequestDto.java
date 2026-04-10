package com.nanashe.backend.dto.alternatives.request;

import java.util.List;

public record AiGenerateRequestDto(String productName, List<String> categories) {}
