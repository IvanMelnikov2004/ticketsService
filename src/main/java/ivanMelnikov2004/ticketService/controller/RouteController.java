package ivanMelnikov2004.ticketService.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ivanMelnikov2004.ticketService.dto.request.TransportModeFilter;
import ivanMelnikov2004.ticketService.entity.Path;
import ivanMelnikov2004.ticketService.service.RouteService;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@RestController
@RequestMapping("/route")
@PreAuthorize("isAuthenticated()")
@RequiredArgsConstructor
public class RouteController {

    private final RouteService routeService;

    @GetMapping("/{from}/{to}")
    public ResponseEntity<List<Path>> findRoutesBetween(@PathVariable("from") Long fromId, @PathVariable("to") Long toId, TransportModeFilter filter, @RequestParam("departure") Long departure) {
        var departureTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(departure), ZoneId.systemDefault());

        return ResponseEntity.ok(routeService.findRouteBetween(fromId, toId, filter, departureTime));
    }
}
