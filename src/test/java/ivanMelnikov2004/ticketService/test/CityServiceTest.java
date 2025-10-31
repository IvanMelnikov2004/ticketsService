package ivanMelnikov2004.ticketService.test;

import ivanMelnikov2004.ticketService.service.CityService;
import ivanMelnikov2004.ticketService.service.impl.CityServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.neo4j.harness.Neo4j;
import org.neo4j.harness.Neo4jBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.neo4j.DataNeo4jTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import ivanMelnikov2004.ticketService.mapper.CityMapper;

import static org.assertj.core.api.Assertions.assertThat;

@DataNeo4jTest
@Import({CityServiceImpl.class, CityMapper.class})
public class CityServiceTest {

    private static Neo4j neo4jEmbeddedServer;

    @Autowired
    private CityService cityService;

    @BeforeAll
    public static void initializeNeo4j() {
        neo4jEmbeddedServer = Neo4jBuilders.newInProcessBuilder()
                .withDisabledServer()
                .build();

        var graphDatabaseService = neo4jEmbeddedServer.defaultDatabaseService();

        graphDatabaseService
                .executeTransactionally("merge (c1: City {name: 'A'});");
        graphDatabaseService
                .executeTransactionally("merge (c1: City {name: 'B'});");
    }

    @DynamicPropertySource
    public static void neo4jProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.neo4j.uri", neo4jEmbeddedServer::boltURI);
        registry.add("spring.neo4j.authentication.username", () -> "neo4j");
        registry.add("spring.neo4j.authentication.password", () -> null);
        registry.add("spring.data.neo4j.database", () -> "neo4j");
    }

    @AfterAll
    public static void stopNeo4j() {
        neo4jEmbeddedServer.close();
    }

    @Test
    public void whenListCities_thenReturnTwoCities() {
        var actual = cityService.listCities(Pageable.ofSize(10)).getContent();

        assertThat(actual.size())
                .isEqualTo(2);
        Assertions.assertThat(actual.get(0).name())
                .isEqualTo("A");
        Assertions.assertThat(actual.get(1).name())
                .isEqualTo("B");
    }

    @Test
    public void whenFindCityByNameA_thenReturnCityResponseOfA() {
        var actual = cityService.findCity("A");

        assertThat(actual.isPresent())
                .isTrue();
        Assertions.assertThat(actual.get().name())
                .isEqualTo("A");
    }
}
