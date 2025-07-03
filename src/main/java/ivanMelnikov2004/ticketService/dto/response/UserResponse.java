package ivanMelnikov2004.ticketService.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import ivanMelnikov2004.ticketService.security.Role;

import java.util.Set;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record UserResponse(
        Long id,
        String lastName,
        String firstName,
        String email,
        String phoneNumber,
        Set<Role> roles
) {
}
