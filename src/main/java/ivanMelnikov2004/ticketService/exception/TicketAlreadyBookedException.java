package ivanMelnikov2004.ticketService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class TicketAlreadyBookedException extends ResponseStatusException {

    public TicketAlreadyBookedException(String reason) {
        super(HttpStatus.CONFLICT, reason);
    }
}