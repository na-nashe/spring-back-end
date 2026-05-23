package com.nanashe.backend.dto.categories.response;

import java.util.List;

public record CategoryResponseDto(Integer id, String title, String icon, List<CategoryResponseDto> children) {
}
