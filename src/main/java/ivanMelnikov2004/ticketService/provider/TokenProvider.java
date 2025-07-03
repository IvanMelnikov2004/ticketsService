package ivanMelnikov2004.ticketService.provider;

import ivanMelnikov2004.ticketService.dto.response.TokenResponse;

public interface TokenProvider {

    TokenResponse generateTokens(String email);
}
