package ivanMelnikov2004.ticketService.repository;

import ivanMelnikov2004.ticketService.entity.Route;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import ivanMelnikov2004.ticketService.dto.response.RouteResponse;

import java.util.List;
import java.util.Optional;

public interface RouteRepository extends Neo4jRepository<Route, Long> {

    @Query("""
        optional match (c1: City)-[r:HAVE_ROUTE_TO*..5 {id: $id}]->(c2: City)
        unwind r as a
        return c2.name as to, a.arrival as arrival, a.departure as departure, a.transportMode as transportMode
    """)
    List<RouteResponse> customFindById(Long id);

    @Query("""
        optional match (c1: City)-[r:HAVE_ROUTE_TO*..5 {id: $id}]->(c2: City)
        return c1.name
    """)
    Optional<String> customFindFromCityNameById(Long id);
}
