package com.nanashe.backend.dto;

import java.util.List;

public record ErrorResponse(String message, List<String> details) {

    public static ErrorResponse of(String message) {
        return new ErrorResponse(message, null);
    }

    public static ErrorResponse withDetails(String message, List<String> details) {
        return new ErrorResponse(message, details);
    }
}
