package ivanMelnikov2004.ticketService.service.impl;

import ivanMelnikov2004.ticketService.mapper.CityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ivanMelnikov2004.ticketService.dto.response.CityResponse;
import ivanMelnikov2004.ticketService.repository.CityRepostiory;
import ivanMelnikov2004.ticketService.service.CityService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {

    private final CityMapper cityMapper;
    private final CityRepostiory cityRepostiory;

    @Override
    @Cacheable("citesList")
    public Page<CityResponse> listCities(Pageable pageable) {
        return cityRepostiory.findAll(pageable).map(cityMapper::toCityResponseDto);
    }

    @Override
    @Cacheable("cityByName")
    public Optional<CityResponse> findCity(String name) {
        return cityRepostiory.findByNameIgnoreCase(name).map(cityMapper::toCityResponseDto);
    }
}
