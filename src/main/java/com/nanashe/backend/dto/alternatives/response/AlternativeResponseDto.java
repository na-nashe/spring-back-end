package com.nanashe.backend.dto.alternatives.response;

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
    private Integer id;
    private String name;
    private String description;
    private String url;
    private String country;
    private String pricingModel;
    private Boolean isCashbackAvailable;
    private String cashbackInfo;
}
