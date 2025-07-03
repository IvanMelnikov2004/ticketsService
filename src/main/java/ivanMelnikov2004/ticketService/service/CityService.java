package ivanMelnikov2004.ticketService.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ivanMelnikov2004.ticketService.dto.response.CityResponse;

import java.util.Optional;

public interface CityService {

    Page<CityResponse> listCities(Pageable pageable);
    Optional<CityResponse> findCity(String name);
}
