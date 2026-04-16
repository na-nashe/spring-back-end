package com.nanashe.backend.security;

import com.nanashe.backend.properties.JwtProperties;
import com.nanashe.backend.util.FileReader;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Slf4j
@Component
@RequiredArgsConstructor
public class RsaKeyReader {

    @Getter
    private PrivateKey privateKey;

    @Getter
    private PublicKey publicKey;

    private final JwtProperties jwtProperties;

    @PostConstruct
    void readKeys() {
        privateKey = loadPrivateKey();
        publicKey = loadPublicKey();
    }

    private PrivateKey loadPrivateKey() {
        try {
            String raw = FileReader.readFileToString(jwtProperties.getPrivateKeyPath());
            return parsePrivateKey(raw);
        } catch (Exception e) {
            String message = "Error parsing private key: " + e.getMessage();
            log.error(message);
            throw new IllegalStateException(message);
        }
    }

    private PublicKey loadPublicKey() {
        try {
            String raw = FileReader.readFileToString(jwtProperties.getPublicKeyPath());
            return parsePublicKey(raw);
        } catch (Exception e) {
            String message = "Error parsing public key: " + e.getMessage();
            log.error(message);
            throw new IllegalStateException(message);
        }
    }

    private static PrivateKey parsePrivateKey(String key) throws NoSuchAlgorithmException, InvalidKeySpecException {
        key = key.replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s+", "");

        byte[] decoded = Base64.getDecoder().decode(key);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(new PKCS8EncodedKeySpec(decoded));
    }

    private static PublicKey parsePublicKey(String key) throws NoSuchAlgorithmException, InvalidKeySpecException {
        key = key.replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s+", "");

        byte[] decoded = Base64.getDecoder().decode(key);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(new X509EncodedKeySpec(decoded));
    }
}
