package com.fin.sight.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fin.sight.common.dto.JwtData;
import com.fin.sight.common.dto.TokenData;
import com.fin.sight.common.exceptions.InvalidTokenException;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;

import java.util.Collections;
import java.util.Objects;

public class JwtUtils {
    private final String key;

    @Value("${google.client.id}")
    private String googleClientId;

    public JwtUtils(String key) {
        this.key = key;
    }

    /**
     * Method to create a JWT token with email, guid, and third-party token.
     *
     * @param email:           the email of the user.
     * @param guid:            the unique identifier for the user.
     * @param thirdPartyToken: the token from a third-party service (optional).
     * @return the generated JWT token.
     */
    public String createJwt(@NotNull final String email, @NotNull final String guid, final String thirdPartyToken) {
        Algorithm algorithm = Algorithm.HMAC256(key);
        return JWT.create().withClaim("email", email).withClaim("guid", guid)
                .withClaim("thirdPartyToken", thirdPartyToken).sign(algorithm);
    }

    /**
     * Method to decode a JWT token and extract user information.
     *
     * @param jwt: the JWT token to be decoded.
     * @return JwtData containing user information extracted from the token.
     */
    public JwtData decodeJwt(@NotNull final String jwt) {
        try {
            DecodedJWT decodedJWT = JWT.decode(jwt);
            String email = decodedJWT.getClaim("email").asString();
            String guid = decodedJWT.getClaim("guid").asString();
            String thirdPartyToken = "";
            if (!decodedJWT.getClaim("thirdPartyToken").isNull()) {
                thirdPartyToken = decodedJWT.getClaim("thirdPartyToken").asString();
            }
            return new JwtData(guid, email, thirdPartyToken);
        } catch (JWTVerificationException e) {
            throw new InvalidTokenException(e);
        }
    }

    /**
     * Method to verify the Google OAuth token and extract user information.
     *
     * @param token: the Google token to be verified.
     * @return the user information extracted from the token.
     */
    public TokenData verifyGoogleOAuthToken(@NotNull final String token) {
        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                    .setAudience(Collections.singletonList(googleClientId))
                    .build();
            GoogleIdToken googleIdToken = verifier.verify(token);
            if (Objects.nonNull(googleIdToken)) {
                GoogleIdToken.Payload payload = googleIdToken.getPayload();
                String firstName = payload.get("given_name").toString();
                String lastName = payload.get("family_name").toString();
                String email = payload.getEmail();
                return new TokenData(firstName, lastName, email);
            }
            throw new InvalidTokenException("Invalid Google OAuth Token");
        } catch (Exception e) {
            throw new InvalidTokenException("Invalid Token / Token could not be verified", e);
        }
    }

}

