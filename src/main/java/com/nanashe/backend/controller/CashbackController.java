package com.nanashe.backend.controller;

import com.nanashe.backend.dto.cashback.CashbackAlternativeResponseDto;
import com.nanashe.backend.service.CashbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cashback")
@RequiredArgsConstructor
public class CashbackController {

    private final CashbackService cashbackService;

    @GetMapping("/alternatives")
    public List<CashbackAlternativeResponseDto> searchCashbackAlternatives(
            @RequestParam(required = false) String q) {
        return cashbackService.searchCashbackAlternatives(q);
    }
}
