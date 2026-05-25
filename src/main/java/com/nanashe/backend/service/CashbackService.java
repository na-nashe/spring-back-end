package com.nanashe.backend.service;

import com.nanashe.backend.dto.cashback.CashbackAlternativeResponseDto;
import com.nanashe.backend.entity.Alternative;
import com.nanashe.backend.repository.AlternativeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CashbackService {

    private final AlternativeRepository alternativeRepository;

    @Transactional(readOnly = true)
    public List<CashbackAlternativeResponseDto> searchCashbackAlternatives(String q) {
        List<Alternative> results = (q != null && !q.isBlank())
                ? alternativeRepository.findByIsCashbackAvailableTrueAndNameContainingIgnoreCase(q)
                : alternativeRepository.findByIsCashbackAvailableTrue();

        return results.stream().map(this::toDto).toList();
    }

    private CashbackAlternativeResponseDto toDto(Alternative alt) {
        return CashbackAlternativeResponseDto.builder()
                .id(alt.getId())
                .name(alt.getName())
                .description(alt.getDescription())
                .country(alt.getOrigin() != null ? alt.getOrigin().getName() : null)
                .cashbackInfo(alt.getCashbackInfo())
                .build();
    }
}
