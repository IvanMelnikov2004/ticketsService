package ivanMelnikov2004.ticketService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CityNotFoundException extends ResponseStatusException {

    public CityNotFoundException(String reason) {
        super(HttpStatus.NOT_FOUND, reason);
    }
}
