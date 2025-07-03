package ivanMelnikov2004.ticketService.service;

import ivanMelnikov2004.ticketService.dto.request.TransportModeFilter;
import ivanMelnikov2004.ticketService.entity.Path;

import java.time.LocalDateTime;
import java.util.List;

public interface RouteService {

    List<Path> findRouteBetween(Long from, Long to, TransportModeFilter filter, LocalDateTime departure);
}
