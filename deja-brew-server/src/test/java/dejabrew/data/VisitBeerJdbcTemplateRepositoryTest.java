package dejabrew.data;

import dejabrew.models.VisitBeer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class VisitBeerJdbcTemplateRepositoryTest {

    @Autowired
    VisitBeerRepository repository;
    @Autowired
    KnownGoodState knownGoodState;
    @BeforeEach
    void setUp() {
        knownGoodState.set();
    }
    @Test
    void shouldAdd(){
        VisitBeer visitBeer = makeVisitBeer();
        assertTrue(repository.add(visitBeer));
    }

    @Test
    void shouldUpdate() {
        VisitBeer visitBeer = makeVisitBeer();
        visitBeer.setVisit_beer_id(1);
        visitBeer.setVisit_id(1);
        visitBeer.setBeer_id(2);
        assertTrue(repository.update(visitBeer));

        visitBeer.setVisit_beer_id(12);
        assertFalse(repository.update(visitBeer));
    }

    @Test
    void shouldDelete() {
        assertTrue(repository.deleteById(4));
        assertFalse(repository.deleteById(4));
    }


    VisitBeer makeVisitBeer() {
        VisitBeer visitBeer = new VisitBeer();
        visitBeer.setVisit_beer_id(1);
        visitBeer.setVisit_id(1);
        visitBeer.setBeer_id(1);
        return visitBeer;
    }
}