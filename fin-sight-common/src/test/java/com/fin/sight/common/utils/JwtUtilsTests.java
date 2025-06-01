package com.fin.sight.common.utils;

import com.fin.sight.common.dto.JwtData;
import com.fin.sight.common.exceptions.InvalidTokenException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class JwtUtilsTests {
    private JwtUtils jwtUtils;

    @BeforeEach
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        jwtUtils = new JwtUtils("SECRET_KEYSECRET_KEYSECRET_KEYSECRET_KEYSECRET_KEY");
    }

    @Test
    public void testGenerateJwt() {
        String token = jwtUtils.createJwt("dummyEmail", "dummyGuid", "dummyToken");
        Assertions.assertNotNull(token);
        JwtData jwtData = jwtUtils.decodeJwt(token);
        Assertions.assertEquals("dummyEmail", jwtData.emailId());
        Assertions.assertEquals("dummyGuid", jwtData.guid());
        Assertions.assertEquals("dummyToken", jwtData.thirdPartyToken());
    }

    @Test
    public void testInvalidJWT() {
        Assertions.assertThrows(InvalidTokenException.class, () -> jwtUtils.decodeJwt("INVALID_TOKEN"));
    }
}
