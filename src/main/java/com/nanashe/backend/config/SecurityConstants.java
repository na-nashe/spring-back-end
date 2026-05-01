package com.nanashe.backend.config;

import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class SecurityConstants {

    public static final String BEARER_PREFIX = "Bearer ";

    public static final List<String> WHITE_LIST = List.of(
            "/alternatives/*",
            "/categories",
            "/auth/*"
    );
}
