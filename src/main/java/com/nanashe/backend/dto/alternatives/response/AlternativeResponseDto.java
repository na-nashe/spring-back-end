package com.nanashe.backend.dto.alternatives.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AlternativeResponseDto {
    private String name;
    private String description;
    private String url;
    private String country;
}
