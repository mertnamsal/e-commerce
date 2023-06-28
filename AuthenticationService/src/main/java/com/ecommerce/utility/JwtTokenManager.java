package com.ecommerce.utility;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ecommerce.exception.AuthenticationException;
import com.ecommerce.exception.EErrorType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;

import javax.validation.Valid;
import java.util.Date;
import java.util.Optional;

@Component
public class JwtTokenManager {
    @Value("${jwt.secret}")
    private String secretKey;
    private final long expirationTimeMillis = 10800000; // Token validity period: 3h

    public Optional<String> generateToken(Long id) {
        try {
            Date now = new Date();
            Date expirationTime = new Date(now.getTime() + expirationTimeMillis);

            Algorithm algorithm = Algorithm.HMAC512(secretKey);
            String token = JWT.create()
                    .withIssuer("ecommerce")
                    .withIssuedAt(now)
                    .withExpiresAt(expirationTime)
                    .withClaim("id", id)
                    .sign(algorithm);

            return Optional.of(token);
        } catch (Exception exception) {
            return Optional.empty();
        }
    }

    public Optional<Long> validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC512(secretKey);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("ecommerce")
                    .build();

            DecodedJWT decodedJWT = verifier.verify(token);
            if (decodedJWT == null) {
                return Optional.empty();
            }
            return Optional.of(decodedJWT.getClaim("id").asLong());
        } catch (Exception e) {
            throw new AuthenticationException(EErrorType.INVALID_TOKEN);
        }
    }
}
