package com.nanashe.backend.dto.cashback;

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
public class CashbackAlternativeResponseDto {
    private Integer id;
    private String name;
    private String description;
    private String country;
    private String cashbackInfo;
}
