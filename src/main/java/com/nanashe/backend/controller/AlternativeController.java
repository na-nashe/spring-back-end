package com.nanashe.backend.controller;

import com.nanashe.backend.dto.AlternativeCountDto;
import com.nanashe.backend.service.AlternativeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/alternatives")
@RequiredArgsConstructor
public class AlternativeController {

    private final AlternativeService alternativeService;

    @GetMapping("/summary")
    public AlternativeCountDto getCount() {
        return alternativeService.getCount();
    }
}
