package com.nanashe.backend.dto.alternatives.response;

import com.nanashe.backend.entity.enums.PricingModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlternativeResponseDto {
    private String name;
    private String description;
    private String url;
    private String country;
    private PricingModel pricingModel;
}
