package ivanMelnikov2004.ticketService.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import ivanMelnikov2004.ticketService.configuration.JwtProperties;
import ivanMelnikov2004.ticketService.exception.TokenInvalidException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;

@Component
public class JwtUtil {

    private final Key key;
    private final UserDetailsService userDetailsService;

    public JwtUtil(JwtProperties jwtProperties, UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
        this.key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(jwtProperties.secretKey().getBytes()));
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getEmail(token));
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (bearerToken != null && bearerToken.startsWith(JwtProperties.BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith((SecretKey) key).build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            throw new TokenInvalidException(HttpStatus.UNAUTHORIZED, "Expired or invalid JWT token");
        }
    }

    public boolean isRefreshToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith((SecretKey) key).build()
                    .parseSignedClaims(token)
                    .getPayload();
            String tokenType = claims.get(JwtProperties.TOKEN_TYPE, String.class);
            return JwtProperties.REFRESH_PREFIX.equals(tokenType);
        } catch (JwtException | IllegalArgumentException e) {
            throw new TokenInvalidException(HttpStatus.UNAUTHORIZED, "Expired or invalid JWT token");
        }
    }

    public String getEmail(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) key).build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

}
