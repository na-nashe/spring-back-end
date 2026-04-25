package com.nanashe.backend.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.nanashe.backend.security.RsaKeyReader;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final RsaKeyReader rsaKeyReader;

    @Value("${access.token.expiration.time}")
    private long accessTokenExpirationTime;

    public String generateAccessToken(UUID userId) {
        Algorithm algorithm = Algorithm.RSA256(
                (RSAPublicKey) rsaKeyReader.getPublicKey(),
                (RSAPrivateKey) rsaKeyReader.getPrivateKey()
        );

        return JWT.create()
                .withSubject(userId.toString())
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plusSeconds(accessTokenExpirationTime))
                .sign(algorithm);
    }
}
