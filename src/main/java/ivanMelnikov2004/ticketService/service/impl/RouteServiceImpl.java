package ivanMelnikov2004.ticketService.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ivanMelnikov2004.ticketService.dto.request.TransportModeFilter;
import ivanMelnikov2004.ticketService.entity.Path;
import ivanMelnikov2004.ticketService.exception.CityNotFoundException;
import ivanMelnikov2004.ticketService.repository.CityRepostiory;
import ivanMelnikov2004.ticketService.service.RouteService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService {

    private final CityRepostiory cityRepostiory;

    @Override
    @Cacheable("routes")
    public List<Path> findRouteBetween(Long from, Long to, TransportModeFilter filter, LocalDateTime departure) {
        var fromCity = cityRepostiory.findById(from)
                .orElseThrow(() -> new CityNotFoundException("City with id " + from + " not found."));
        var toCity = cityRepostiory.findById(to)
                .orElseThrow(() -> new CityNotFoundException("City with id " + to + " not found."));

        if (filter.mix() != null && filter.mix()) {
            return cityRepostiory.calculateShortestPathBetween(fromCity.getName(), toCity.getName(), departure);
        }

        return cityRepostiory.calculateShortestPathBetween(fromCity.getName(), toCity.getName(), filter.mode(), departure);
    }
}
