package ivanMelnikov2004.ticketService.service;

import ivanMelnikov2004.ticketService.dto.request.AuthenticationRequest;
import ivanMelnikov2004.ticketService.dto.request.RefreshTokenRequest;
import ivanMelnikov2004.ticketService.dto.response.TokenResponse;

public interface AuthenticationService {

    TokenResponse authenticate(AuthenticationRequest authenticationRequest);
    TokenResponse refresh(RefreshTokenRequest refreshTokenRequest);
}
