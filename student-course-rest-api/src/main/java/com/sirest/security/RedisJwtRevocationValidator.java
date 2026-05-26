package com.sirest.security;

import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class RedisJwtRevocationValidator implements OAuth2TokenValidator<Jwt> {

    private final TokenRevocationService tokenRevocationService;

    public RedisJwtRevocationValidator(TokenRevocationService tokenRevocationService) {
        this.tokenRevocationService = tokenRevocationService;
    }

    @Override
    public OAuth2TokenValidatorResult validate(Jwt token) {
        String jti = token.getId();

        if (jti == null || jti.isBlank()) {
            return OAuth2TokenValidatorResult.failure(new OAuth2Error(
                    OAuth2ErrorCodes.INVALID_TOKEN,
                    "Missing token identifier",
                    null
            ));
        }

        if (tokenRevocationService.isRevokedJti(jti)) {
            return OAuth2TokenValidatorResult.failure(new OAuth2Error(
                    OAuth2ErrorCodes.INVALID_TOKEN,
                    "Token has been revoked",
                    null
            ));
        }

        return OAuth2TokenValidatorResult.success();
    }
}
