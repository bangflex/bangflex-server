package springbootmonolithic.security.domain.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class RefreshTokenService {
    private final StringRedisTemplate redisTemplate;
    private final Environment environment;

    @Autowired
    public RefreshTokenService(StringRedisTemplate redisTemplate, Environment environment) {
        this.redisTemplate = redisTemplate;
        this.environment = environment;
    }

    public void saveRefreshToken(String loginId, String refreshToken) {
        long expirationTime = Long.parseLong(Objects.requireNonNull(environment.getProperty("token.refresh.expiration_time")));
        redisTemplate.opsForValue().set(loginId, refreshToken, expirationTime, TimeUnit.MILLISECONDS);
    }

    public boolean checkRefreshTokenInRedis(String loginId, String refreshToken) {
        return refreshToken.equals(redisTemplate.opsForValue().get(loginId));
    }
}
