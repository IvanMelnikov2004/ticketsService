package ivanMelnikov2004.ticketService.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import ivanMelnikov2004.ticketService.dto.request.TicketRequest;
import ivanMelnikov2004.ticketService.dto.response.RouteResponse;
import ivanMelnikov2004.ticketService.dto.response.TicketResponse;
import ivanMelnikov2004.ticketService.dto.response.UserResponse;
import ivanMelnikov2004.ticketService.entity.Ticket;
import ivanMelnikov2004.ticketService.entity.Ticket.TicketKey;
import ivanMelnikov2004.ticketService.entity.TransportMode;
import ivanMelnikov2004.ticketService.entity.User;
import ivanMelnikov2004.ticketService.exception.RouteNotFoundException;
import ivanMelnikov2004.ticketService.mapper.TicketMapper;
import ivanMelnikov2004.ticketService.mapper.UserMapper;
import ivanMelnikov2004.ticketService.repository.RouteRepository;
import ivanMelnikov2004.ticketService.repository.TicketRepository;
import ivanMelnikov2004.ticketService.security.Role;
import ivanMelnikov2004.ticketService.service.impl.TicketServiceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {

    @Mock
    private RouteRepository routeRepository;

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private TicketMapper ticketMapper;

    @Mock
    private UserMapper userMapper;

    @Mock
    private Pageable pageable;

    @InjectMocks
    private TicketServiceImpl ticketService;

    private final Long userId = 1L;
    private final Long routeId = 100L;
    private final UserResponse userResponse = new UserResponse(
            userId,
            "Doe",
            "John",
            "john.doe@example.com",
            "+1234567890",
            Set.of(Role.MEMBER)
    );

    private final User user = createTestUser(userId);
    private final TicketKey ticketKey = new TicketKey(userId, routeId);

    private final LocalDateTime testTime = LocalDateTime.now();
    private final RouteResponse testRoute = new RouteResponse(
            "CityB",
            testTime.plusHours(2),
            testTime.plusHours(1),
            TransportMode.BUS
    );

    private User createTestUser(Long id) {
        User user = new User();
        user.setId(id);
        user.setLastName("Doe");
        user.setFirstName("John");
        user.setEmail("john.doe@example.com");
        user.setPhoneNumber("+1234567890");
        user.setRoles(Set.of(Role.MEMBER));
        return user;
    }

    @Test
    void bookTicket_shouldMapRouteResponseCorrectly() {
        TicketRequest request = new TicketRequest(routeId, null);
        RouteResponse route = new RouteResponse("CityB", testTime, testTime, TransportMode.RAILWAY);

        when(userMapper.toUser(userResponse)).thenReturn(user);
        when(routeRepository.customFindById(routeId)).thenReturn(List.of(route));
        when(ticketRepository.save(any())).thenReturn(new Ticket());
        when(ticketMapper.toTicketResponseDto(any())).thenReturn(new TicketResponse(ticketKey));

        List<TicketResponse> result = ticketService.bookTicket(userResponse, request);

        assertThat(result)
                .first()
                .satisfies(res -> {
                    assertThat(res.getKey()).isEqualTo(ticketKey);
                    assertThat(res.getRoutes()).isNull();
                });
    }

    @Test
    void bookTicketOne_shouldThrowRouteNotFoundException() {
        when(routeRepository.customFindById(routeId)).thenReturn(List.of());

        assertThatThrownBy(() -> ticketService.bookTicket(userResponse, new TicketRequest(routeId, null)))
                .isInstanceOf(RouteNotFoundException.class)
                .hasMessage("404 NOT_FOUND \"Route with id " + routeId + " not found!\"");
    }

    @Test
    void listTickets_shouldReturnEnhancedResponse() {
        Ticket ticket = new Ticket();
        ticket.setKey(ticketKey);
        ticket.setOwner(user);

        Page<Ticket> ticketPage = new PageImpl<>(List.of(ticket));
        List<RouteResponse> routes = List.of(testRoute);

        when(ticketRepository.findAllByKeyOwnerId(userId, pageable)).thenReturn(ticketPage);
        when(ticketMapper.toTicketResponseDto(ticket)).thenReturn(
                new TicketResponse(ticketKey)
        );
        when(routeRepository.customFindById(routeId)).thenReturn(routes);
        when(routeRepository.customFindFromCityNameById(routeId)).thenReturn(Optional.of("CityA"));

        Page<TicketResponse> result = ticketService.listTickets(userResponse, pageable);

        assertThat(result.getContent())
                .first()
                .satisfies(res -> {
                    assertThat(res.getRoutes())
                            .first()
                            .satisfies(route -> {
                                assertThat(route.to()).isEqualTo("CityB");
                                assertThat(route.transportMode()).isEqualTo(TransportMode.BUS);
                                assertThat(route.departure()).isEqualTo(testTime.plusHours(1));
                            });
                    assertThat(res.getFrom()).isEqualTo("CityA");
                    assertThat(res.getKey()).isEqualTo(ticketKey);
                });
    }
}