package ivanMelnikov2004.ticketService.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

import java.time.LocalDateTime;

@Data
@RelationshipProperties
@AllArgsConstructor
public class Route {

    @Id
    @GeneratedValue
    private Long id;

    @TargetNode
    private City city;

    private TransportMode transportMode;
    private LocalDateTime departure;
    private LocalDateTime arrival;
}
