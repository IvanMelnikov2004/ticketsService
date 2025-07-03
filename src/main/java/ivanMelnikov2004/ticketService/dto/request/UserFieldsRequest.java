package ivanMelnikov2004.ticketService.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record UserFieldsRequest(
        @NotBlank(message = "Last name cannot be blank.")
        String lastName,
        @NotBlank(message = "First name cannot be blank.")
        String firstName,
        @Pattern(regexp = "\\d{11}")
        @NotBlank(message = "Phone number cannot be blank.")
        String phoneNumber,
        @Email
        @NotBlank(message = "Email cannot be blank.")
        String email,
        @NotBlank(message = "Password cannot be blank.")
        @Length(min = 8)
        String password
) {
}
