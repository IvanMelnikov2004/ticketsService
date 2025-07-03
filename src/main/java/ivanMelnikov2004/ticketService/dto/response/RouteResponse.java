package ivanMelnikov2004.ticketService.dto.response;

import ivanMelnikov2004.ticketService.entity.TransportMode;

import java.time.LocalDateTime;

public record RouteResponse(String to, LocalDateTime arrival, LocalDateTime departure, TransportMode transportMode) {
}
