package ivanMelnikov2004.ticketService.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ivanMelnikov2004.ticketService.dto.response.UserResponse;
import ivanMelnikov2004.ticketService.exception.UserNotFoundException;
import ivanMelnikov2004.ticketService.service.UserService;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        return ResponseEntity.ok(userService.findUser(authentication.getName())
                .orElseThrow(() -> new UserNotFoundException("User not found")));
    }

    @Secured("ADMIN")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getAnyUser(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.findUser(id)
                .orElseThrow(() -> new UserNotFoundException("User not found")));
    }
}
