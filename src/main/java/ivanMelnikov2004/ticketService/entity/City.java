package ivanMelnikov2004.ticketService.entity;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.*;

import java.util.List;

@Data
@Node("City")
public class City {

    @Id
    @GeneratedValue
    private Long id;

    @Property("name")
    private String name;

    @Relationship(type = "HAVE_ROUTE_TO")
    private List<Route> routes;
}
