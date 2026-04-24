package com.nanashe.backend.config;

import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
class SecurityConstants {

    public static final List<String> WHITE_LIST = List.of(
            "/alternatives/summary",
            "/categories",
            "/auth/signup",
            "/auth/signin"
    );
}
