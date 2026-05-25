package com.nanashe.backend.controller;

import com.nanashe.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth/email")
@RequiredArgsConstructor
public class EmailController {
    private final UserService userService;

    @GetMapping("/verify")
    public ResponseEntity<Map<String, String>> verifyEmail(@RequestParam String token) {
        userService.verifyEmail(token);
        return ResponseEntity.ok(Map.of("message", "Email verified successfully"));
    }
}
