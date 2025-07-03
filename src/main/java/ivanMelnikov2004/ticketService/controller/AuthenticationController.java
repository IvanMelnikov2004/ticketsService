package ivanMelnikov2004.ticketService.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ivanMelnikov2004.ticketService.dto.request.AuthenticationRequest;
import ivanMelnikov2004.ticketService.dto.request.RefreshTokenRequest;
import ivanMelnikov2004.ticketService.dto.request.UserFieldsRequest;
import ivanMelnikov2004.ticketService.dto.response.TokenResponse;
import ivanMelnikov2004.ticketService.dto.response.UserResponse;
import ivanMelnikov2004.ticketService.service.AuthenticationService;
import ivanMelnikov2004.ticketService.service.UserService;

@RestController
@RequestMapping("/authentication")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserFieldsRequest request) {
        return ResponseEntity.ok(userService.createUser(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<TokenResponse> authenticate(@Valid @RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refresh(@Valid @RequestBody RefreshTokenRequest request) {
        return ResponseEntity.ok(authenticationService.refresh(request));
    }
}
