package dejabrew.domain;

import dejabrew.data.VisitRepository;
import dejabrew.models.Visit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class VisitServiceTest {
    @Autowired
    VisitService service;

    @MockBean
    VisitRepository repository;

    @Test
    void shouldAdd() {
        Visit visit = makeVisit();
        Visit mockOut = makeVisit();
        mockOut.setVisitId(5);

        when(repository.add(visit)).thenReturn(mockOut);

        Result<Visit> actual = service.add(visit);
        assertEquals(ResultType.SUCCESS, actual.getType());
        assertEquals(mockOut, actual.getPayload());
    }

    @Test
    void shouldNotUpdate() {
        Visit visit = makeVisit();
        Result<Visit> actual = service.update(visit);
        assertEquals(ResultType.INVALID, actual.getType());

        visit = makeVisit();
        visit.setVisitId(4);
        visit.setBreweryId(null);
        actual = service.update(visit);
        assertEquals(ResultType.INVALID, actual.getType());
    }

    @Test
    void shouldUpdate() {
        Visit visit= makeVisit();
        visit.setVisitId(3);
        visit.setDate(LocalDate.of(2022,9,15));

        when(repository.update(visit)).thenReturn(true);

        Result<Visit> actual = service.update(visit);
        assertEquals(ResultType.SUCCESS, actual.getType());
    }

    Visit makeVisit() {
        Visit visit = new Visit();
        visit.setUserId(5);
        visit.setBreweryId("10-56-brewing-company-knox");
        visit.setDate(LocalDate.of(2022,9,13));
        return visit;
    }

}