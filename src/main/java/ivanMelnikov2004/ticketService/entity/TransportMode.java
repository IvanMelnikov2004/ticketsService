package ivanMelnikov2004.ticketService.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TransportMode {
    PLANE(100),
    RAILWAY(300),
    BUS(30);

    private final int size;
}
