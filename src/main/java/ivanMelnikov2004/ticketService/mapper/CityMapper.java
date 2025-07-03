package ivanMelnikov2004.ticketService.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ivanMelnikov2004.ticketService.dto.request.CityRequest;
import ivanMelnikov2004.ticketService.dto.response.CityResponse;
import ivanMelnikov2004.ticketService.entity.City;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CityMapper {

    CityResponse toCityResponseDto(City city);
    City toCity(CityRequest cityRequest);
    City toCity(CityResponse cityResponse);
}
