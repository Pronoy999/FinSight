package com.fin.sight.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fin.sight.common.dto.JwtData;
import com.fin.sight.common.exceptions.InvalidTokenException;
import jakarta.validation.constraints.NotNull;

import java.nio.charset.StandardCharsets;

public class JwtUtils {
    private final String key;

    public JwtUtils(String key) {
        this.key = key;
    }

    public String createJwt(@NotNull final String email, @NotNull final String guid) {
        Algorithm algorithm = Algorithm.HMAC256(key);
        return JWT.create().withClaim("email", email).withClaim("guid", guid).sign(algorithm);
    }

    public JwtData decodeJwt(@NotNull final String jwt) {
        try {
            DecodedJWT decodedJWT = JWT.decode(jwt);
            String email = decodedJWT.getClaim("email").asString();
            String guid = decodedJWT.getClaim("guid").asString();
            return new JwtData(guid, email);
        } catch (JWTVerificationException e) {
            throw new InvalidTokenException(e);
        }
    }
}

