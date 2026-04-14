package com.nanashe.backend.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileReader {

    private FileReader() {}

    public static String readFileToString(String path) throws IOException {
        if (path.startsWith("classpath:")) {
            String resourcePath = path.substring("classpath:".length());
            try (InputStream is = FileReader.class.getClassLoader().getResourceAsStream(resourcePath)) {
                if (is == null) {
                    throw new IOException("Classpath resource not found: " + resourcePath);
                }
                return new String(is.readAllBytes(), StandardCharsets.UTF_8);
            }
        }
        return Files.readString(Path.of(path));
    }
}
