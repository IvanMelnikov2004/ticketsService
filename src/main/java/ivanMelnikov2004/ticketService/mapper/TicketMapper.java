package ivanMelnikov2004.ticketService.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ivanMelnikov2004.ticketService.dto.response.TicketResponse;
import ivanMelnikov2004.ticketService.entity.Ticket;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TicketMapper {

    TicketResponse toTicketResponseDto(Ticket ticket);
}
