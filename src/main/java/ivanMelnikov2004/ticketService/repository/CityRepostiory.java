package ivanMelnikov2004.ticketService.repository;

import ivanMelnikov2004.ticketService.entity.City;
import ivanMelnikov2004.ticketService.entity.Path;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import ivanMelnikov2004.ticketService.entity.TransportMode;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CityRepostiory extends Neo4jRepository<City, Long> {

    @Query("""
            match s=shortestPath((src: City {name: $from})-[r:HAVE_ROUTE_TO*..5]->(dst: City {name: $to}))
            where 
                all(rel in r where rel.transportMode = $mode and rel.departure >= $departure)
                AND
                all(idx IN range(1, size(r)-1) WHERE r[idx].departure >= r[idx-1].arrival)
            UNWIND range(0, size(nodes(s)) - 1) AS idx
            RETURN
                nodes(s)[idx] AS city,
                (CASE WHEN idx > 0 THEN r[idx-1] ELSE r[0] END).id as rid,
                (CASE WHEN idx > 0 THEN r[idx-1] ELSE r[0] END).arrival as arrival,
                (CASE WHEN idx > 0 THEN r[idx-1] ELSE r[0] END).departure as departure,
                (CASE WHEN idx > 0 THEN r[idx-1] ELSE r[0] END).transportMode as transportMode
    """)
    List<Path> calculateShortestPathBetween(String from, String to, TransportMode mode, LocalDateTime departure);

    @Query("""
            match s=shortestPath((src: City {name: $from})-[r:HAVE_ROUTE_TO*..5]->(dst: City {name: $to}))
            where 
                all(rel in r where rel.departure >= $departure)
                AND
                all(idx IN range(1, size(r)-1) WHERE r[idx].departure >= r[idx-1].arrival)
            UNWIND range(0, size(nodes(s)) - 1) AS idx
            RETURN
                nodes(s)[idx] AS city,
                (CASE WHEN idx > 0 THEN r[idx-1] ELSE r[0] END).id as rid,
                (CASE WHEN idx > 0 THEN r[idx-1] ELSE r[0] END).arrival as arrival,
                (CASE WHEN idx > 0 THEN r[idx-1] ELSE r[0] END).departure as departure,
                (CASE WHEN idx > 0 THEN r[idx-1] ELSE r[0] END).transportMode as transportMode
    """)
    List<Path> calculateShortestPathBetween(String from, String to, LocalDateTime departure);

    Optional<City> findByNameIgnoreCase(String name);
}
