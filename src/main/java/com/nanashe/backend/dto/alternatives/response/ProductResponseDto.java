package com.nanashe.backend.dto.alternatives.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ProductResponseDto {
    private Integer id;
    private String name;
    private String category;
    private String origin;
    private List<String> aliases;
    private List<AlternativeResponseDto> alternatives;
}
