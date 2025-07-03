package ivanMelnikov2004.ticketService.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record TokenResponse(
        String accessToken,
        long accessTokenExpiration,
        String refreshToken,
        long refreshTokenExpiration
) { }
