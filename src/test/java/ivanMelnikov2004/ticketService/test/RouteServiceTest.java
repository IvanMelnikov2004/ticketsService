package ivanMelnikov2004.ticketService.test;

import ivanMelnikov2004.ticketService.dto.request.TransportModeFilter;
import ivanMelnikov2004.ticketService.entity.City;
import ivanMelnikov2004.ticketService.entity.Path;
import ivanMelnikov2004.ticketService.entity.TransportMode;
import ivanMelnikov2004.ticketService.exception.CityNotFoundException;
import ivanMelnikov2004.ticketService.repository.CityRepostiory;
import ivanMelnikov2004.ticketService.service.impl.RouteServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RouteServiceTest {

    @Mock
    private CityRepostiory cityRepository;

    @InjectMocks
    private RouteServiceImpl routeService;

    private final Long validCityId = 1L;
    private final Long invalidCityId = 999L;
    private final String cityName = "TestCity";
    private final LocalDateTime departure = LocalDateTime.now();
    private final TransportMode mode = TransportMode.BUS;

    private City createTestCity(Long id, String name) {
        City city = new City();
        city.setId(id);
        city.setName(name);
        return city;
    }

    private Path createTestPath(City city) {
        return new Path(
                city,
                1L,
                departure.plusHours(1),
                departure,
                TransportMode.BUS
        );
    }

    @Test
    void findRouteBetween_whenFromCityNotFound_throwsException() {
        when(cityRepository.findById(invalidCityId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> routeService.findRouteBetween(invalidCityId, validCityId,
                new TransportModeFilter(mode, false), departure))
                .isInstanceOf(CityNotFoundException.class)
                .hasMessage("404 NOT_FOUND \"City with id " + invalidCityId + " not found.\"");
    }

    @Test
    void findRouteBetween_whenToCityNotFound_throwsException() {
        when(cityRepository.findById(validCityId)).thenReturn(Optional.of(createTestCity(validCityId, cityName)));
        when(cityRepository.findById(invalidCityId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> routeService.findRouteBetween(validCityId, invalidCityId,
                new TransportModeFilter(mode, false), departure))
                .isInstanceOf(CityNotFoundException.class)
                .hasMessage("404 NOT_FOUND \"City with id " + invalidCityId + " not found.\"");
    }

    @Test
    void findRouteBetween_whenMixTrue_callsCalculateShortestPathWithoutMode() {
        City fromCity = createTestCity(validCityId, cityName);
        City toCity = createTestCity(2L, "CityB");
        TransportModeFilter filter = new TransportModeFilter(null, true);
        Path testPath = createTestPath(fromCity);
        List<Path> expectedPaths = List.of(testPath);

        when(cityRepository.findById(validCityId)).thenReturn(Optional.of(fromCity));
        when(cityRepository.findById(2L)).thenReturn(Optional.of(toCity));
        when(cityRepository.calculateShortestPathBetween(cityName, "CityB", departure))
                .thenReturn(expectedPaths);

        List<Path> result = routeService.findRouteBetween(validCityId, 2L, filter, departure);

        verify(cityRepository).calculateShortestPathBetween(cityName, "CityB", departure);
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expectedPaths);
    }

    @Test
    void findRouteBetween_whenMixFalse_callsCalculateShortestPathWithMode() {
        City fromCity = createTestCity(validCityId, cityName);
        City toCity = createTestCity(2L, "CityB");
        TransportModeFilter filter = new TransportModeFilter(mode, false);
        Path testPath = createTestPath(fromCity);
        List<Path> expectedPaths = List.of(testPath);

        when(cityRepository.findById(validCityId)).thenReturn(Optional.of(fromCity));
        when(cityRepository.findById(2L)).thenReturn(Optional.of(toCity));
        when(cityRepository.calculateShortestPathBetween(cityName, "CityB", mode, departure))
                .thenReturn(expectedPaths);

        List<Path> result = routeService.findRouteBetween(validCityId, 2L, filter, departure);

        verify(cityRepository).calculateShortestPathBetween(cityName, "CityB", mode, departure);
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expectedPaths);
    }
}
