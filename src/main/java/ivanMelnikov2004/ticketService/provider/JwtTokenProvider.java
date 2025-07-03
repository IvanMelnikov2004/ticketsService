package ivanMelnikov2004.ticketService.provider;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import ivanMelnikov2004.ticketService.configuration.JwtProperties;
import ivanMelnikov2004.ticketService.dto.response.TokenResponse;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider implements TokenProvider {

    private final Key key;
    private final JwtProperties jwtProperties;

    public JwtTokenProvider(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
        this.key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(jwtProperties.secretKey().getBytes()));
    }

    @Override
    public TokenResponse generateTokens(String email) {
        return new TokenResponse(
                createToken(email, jwtProperties.getAccessExpiration(), JwtProperties.ACCESS_PREFIX),
                jwtProperties.getAccessExpiration(),
                createToken(email, jwtProperties.getRefreshExpiration(), JwtProperties.REFRESH_PREFIX),
                jwtProperties.getRefreshExpiration()
        );
    }

    private String createToken(String email, long tokenExpiration, String tokenType) {
        Date now = new Date();
        Date lifetime = new Date(now.getTime() + tokenExpiration);

        return Jwts.builder()
                .subject(email)
                .issuedAt(now)
                .expiration(lifetime)
                .signWith(key)
                .claim(JwtProperties.TOKEN_TYPE, tokenType)
                .compact();
    }
}
