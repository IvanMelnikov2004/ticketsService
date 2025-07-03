package ivanMelnikov2004.ticketService.dto.request;

import ivanMelnikov2004.ticketService.entity.TransportMode;

public record TransportModeFilter(
        TransportMode mode,
        Boolean mix
) {
}
