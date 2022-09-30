package dejabrew.data;

import static org.junit.jupiter.api.Assertions.*;


import dejabrew.models.Visit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
class VisitJdbcTemplateRepositoryTest {

    final static int NEXT_VISIT_ID = 5;
    @Autowired
    VisitJdbcTemplateRepository visitRepository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setUp() {
        knownGoodState.set();
    }

    @Test
    void shouldFindById() {
        Visit actual = visitRepository.findById(1);
        assertNotNull(actual);
    }

    @Test
    void shouldAdd(){
        Visit visit = makeVisit();
        Visit actual = visitRepository.add(visit);
        assertNotNull(visit);
        assertEquals(NEXT_VISIT_ID, actual.getVisitId());
    }

    @Test
    void shouldUpdate() {
        Visit visit = new Visit();
        visit.setVisitId(3);
        visit.setDate(LocalDate.of(1999,5,4));
        assertTrue(visitRepository.update(visit));
        visit.setVisitId(16);
        visit.setDate(LocalDate.of(1999,5,4));
        assertFalse(visitRepository.update(visit));
    }

    @Test
    void shouldDelete() {
        assertTrue(visitRepository.deleteById(4));
        assertFalse(visitRepository.deleteById(4));
    }

    Visit makeVisit() {
        Visit visit = new Visit();
        visit.setUserId(5);
        visit.setBreweryId("10-56-brewing-company-knox");
        visit.setDate(LocalDate.of(2022,9,13));
        return visit;
    }
}