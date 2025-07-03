package ivanMelnikov2004.ticketService.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;
import java.util.Map;

@ConfigurationProperties(prefix = JwtProperties.PREFIX)
public record JwtProperties(
        String secretKey,
        Map<String, Long> expiration
) implements Serializable {

    public static final String PREFIX = "jwt";
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String TOKEN_TYPE = "token_type";
    public static final String ACCESS_PREFIX = "access";
    public static final String REFRESH_PREFIX = "refresh";

    public Long getAccessExpiration() {
        return expiration.getOrDefault(ACCESS_PREFIX, 50000L);
    }

    public Long getRefreshExpiration() {
        return expiration.getOrDefault(REFRESH_PREFIX, 600000L);
    }

}
