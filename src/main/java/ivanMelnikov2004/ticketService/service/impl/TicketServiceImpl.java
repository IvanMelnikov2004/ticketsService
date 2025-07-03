package ivanMelnikov2004.ticketService.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ivanMelnikov2004.ticketService.dto.request.TicketRequest;
import ivanMelnikov2004.ticketService.dto.response.TicketResponse;
import ivanMelnikov2004.ticketService.dto.response.UserResponse;
import ivanMelnikov2004.ticketService.entity.Ticket;
import ivanMelnikov2004.ticketService.exception.RouteNotFoundException;
import ivanMelnikov2004.ticketService.exception.TicketAlreadyBookedException;
import ivanMelnikov2004.ticketService.mapper.TicketMapper;
import ivanMelnikov2004.ticketService.mapper.UserMapper;
import ivanMelnikov2004.ticketService.repository.RouteRepository;
import ivanMelnikov2004.ticketService.repository.TicketRepository;
import ivanMelnikov2004.ticketService.service.TicketService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final RouteRepository routeRepository;
    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;
    private final UserMapper userMapper;

    @Override
    public List<TicketResponse> bookTicket(UserResponse owner, TicketRequest ticketRequest) {
        if (ticketRequest.routeIds() != null) {
            List<TicketResponse> list = new ArrayList<>();

            for (var routeId : ticketRequest.routeIds()) {
                list.add(bookTicketOne(routeId, owner));
            }

            return list;
        } else {
            return List.of(bookTicketOne(ticketRequest.routeId(), owner));
        }
    }

    private TicketResponse bookTicketOne(Long routeId, UserResponse owner) {
        var list = routeRepository.customFindById(routeId);

        if (list == null || list.isEmpty())
            throw new RouteNotFoundException("Route with id " + routeId + " not found!");

        if (ticketRepository.existsByKeyOwnerIdAndKeyRouteId(owner.id(), routeId))
            throw new TicketAlreadyBookedException("Ticket for this route already booked");

        var ticket = new Ticket();

        ticket.setKey(new Ticket.TicketKey(owner.id(), routeId));
        ticket.setOwner(userMapper.toUser(owner));

        return ticketMapper.toTicketResponseDto(ticketRepository.save(ticket));
    }

    @Override
    public void cancelTicket(UserResponse owner, TicketRequest ticketRequest) {
        var routeId = ticketRequest.routeId();

        ticketRepository.deleteById(new Ticket.TicketKey(owner.id(), routeId));
    }

    @Override
    public Page<TicketResponse> listTickets(UserResponse owner, Pageable pageable) {
        return ticketRepository.findAllByKeyOwnerId(owner.id(), pageable)
                .map(ticketMapper::toTicketResponseDto)
                .map(ticketResponse -> ticketResponse
                        .withRoutes(
                                routeRepository.customFindById(ticketResponse.getKey().routeId())
                        )
                        .withFrom(
                                routeRepository.customFindFromCityNameById(ticketResponse.getKey().routeId())
                                        .get()
                        )
                );
    }
}
