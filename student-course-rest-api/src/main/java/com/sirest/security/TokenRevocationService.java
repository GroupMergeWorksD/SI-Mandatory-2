package com.sirest.security;

import java.time.Duration;
import java.util.Date;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class TokenRevocationService {

    private static final String REVOKED_TOKEN_PREFIX = "revoked:";

    private final StringRedisTemplate redisTemplate;
    private final JwtService jwtService;

    public TokenRevocationService(StringRedisTemplate redisTemplate, JwtService jwtService) {
        this.redisTemplate = redisTemplate;
        this.jwtService = jwtService;
    }

    public void revokeToken(String token) {
        String jti = jwtService.extractJti(token);
        Date expiration = jwtService.extractExpiration(token);

        long ttlMillis = expiration.getTime() - System.currentTimeMillis();
        if (ttlMillis > 0) {
            redisTemplate.opsForValue().set(buildRevokedKey(jti), "true", Duration.ofMillis(ttlMillis));
        }
    }

    public boolean isRevoked(String token) {
        return isRevokedJti(jwtService.extractJti(token));
    }

    public boolean isRevokedJti(String jti) {
        if (jti == null || jti.isBlank()) {
            return false;
        }

        return redisTemplate.hasKey(buildRevokedKey(jti));
    }

    private String buildRevokedKey(String jti) {
        return REVOKED_TOKEN_PREFIX + jti;
    }
}