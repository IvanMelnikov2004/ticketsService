package ivanMelnikov2004.ticketService.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "tickets")
public class Ticket {

    @EmbeddedId
    private TicketKey key;

    @ManyToOne
    @MapsId("ownerId")
    private User owner;

    @Embeddable
    public record TicketKey(Long ownerId, Long routeId) implements Serializable {}
}
