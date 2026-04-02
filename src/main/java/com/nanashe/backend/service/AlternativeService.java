package com.nanashe.backend.service;

import com.nanashe.backend.dto.AlternativeCountDto;
import com.nanashe.backend.repository.AlternativeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlternativeService {

    private final AlternativeRepository alternativeRepository;

    public AlternativeCountDto getCount() {
        return new AlternativeCountDto(alternativeRepository.count());
    }
}
