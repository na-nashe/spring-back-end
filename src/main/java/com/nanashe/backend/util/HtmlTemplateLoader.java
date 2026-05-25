package com.nanashe.backend.util;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@UtilityClass
public class HtmlTemplateLoader {
    private static final String TEMPLATE_PATH = "templates/%s";

    public String loadTemplate(@NonNull String templateName) {
        try {
            return new ClassPathResource(TEMPLATE_PATH.formatted(templateName)).getContentAsString(StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load email template: " + templateName, e);
        }
    }
}
