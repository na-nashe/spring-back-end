package com.nanashe.backend.dto;

import java.util.List;

public record AiGenerateRequestDto(String productName, List<String> categories) {}
