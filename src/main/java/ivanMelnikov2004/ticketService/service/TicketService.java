package ivanMelnikov2004.ticketService.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ivanMelnikov2004.ticketService.dto.request.TicketRequest;
import ivanMelnikov2004.ticketService.dto.response.TicketResponse;
import ivanMelnikov2004.ticketService.dto.response.UserResponse;

import java.util.List;

public interface TicketService {

    List<TicketResponse> bookTicket(UserResponse owner, TicketRequest ticketRequest);
    void cancelTicket(UserResponse owner, TicketRequest ticketRequest);
    Page<TicketResponse> listTickets(UserResponse owner, Pageable pageable);
}
