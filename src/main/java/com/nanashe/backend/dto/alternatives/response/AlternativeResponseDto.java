package com.nanashe.backend.dto.alternatives.response;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("pricing_model")
    private String pricingModel;
    private Boolean isCashbackAvailable;
    private String cashbackInfo;
}
