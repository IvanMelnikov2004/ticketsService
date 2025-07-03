package ivanMelnikov2004.ticketService.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ivanMelnikov2004.ticketService.dto.response.CityResponse;
import ivanMelnikov2004.ticketService.exception.CityNotFoundException;
import ivanMelnikov2004.ticketService.service.CityService;

@RestController
@RequestMapping("/city")
@PreAuthorize("isAuthenticated()")
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;

    @GetMapping
    public ResponseEntity<Page<CityResponse>> listCities(Pageable pageable) {
        return ResponseEntity.ok(cityService.listCities(pageable));
    }

    @GetMapping("/{name}")
    public ResponseEntity<CityResponse> findCity(@PathVariable("name") String name) {
        return ResponseEntity.ok(cityService.findCity(name)
                .orElseThrow(() -> new CityNotFoundException("City with name " + name + " not found.")));
    }
}
