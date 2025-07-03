package ivanMelnikov2004.ticketService.repository;

import ivanMelnikov2004.ticketService.entity.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface TicketRepository extends CrudRepository<Ticket, Ticket.TicketKey> {

    boolean existsByKeyOwnerIdAndKeyRouteId(Long ownerId, Long routeId);
    Page<Ticket> findAllByKeyOwnerId(Long ownerId, Pageable pageable);
}
