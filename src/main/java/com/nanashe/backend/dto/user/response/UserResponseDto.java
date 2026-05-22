package com.nanashe.backend.dto.user.response;

import java.time.OffsetDateTime;
import java.util.UUID;

public record UserResponseDto(
        UUID id,
        String username,
        String email,
        String avatar,
        OffsetDateTime joined
) {}
