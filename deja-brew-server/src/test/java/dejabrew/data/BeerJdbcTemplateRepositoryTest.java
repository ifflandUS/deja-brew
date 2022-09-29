package dejabrew.data;

import dejabrew.models.Beer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BeerJdbcTemplateRepositoryTest {

    @Autowired
    BeerJdbcTemplateRepository repository;

    @Autowired
    JdbcTemplate template;

    static boolean hasRun = false;

    @BeforeEach
    void oneTimeSetup() {
        if (!hasRun) {
            hasRun = true;
            template.update("call known_good_state();");
        }
    }

    @Test
    void shouldFindBeersByBrewery(){
        assertEquals(2, repository.findBeersByBrewery("madtree-brewing-cincinnati").size());

        assertEquals(0, repository.findBeersByBrewery("nothing-here").size());
    }

    @Test
    void shouldFindBeersById(){
        Beer expected = new Beer("Miller", 4.0, "American Pilsner", "madtree-brewing-cincinnati");
        expected.setBeerId(1);
        assertEquals(expected, repository.findBeersById(1));
    }

    @Test
    void shouldAddBeer(){
        Beer beer = new Beer("Yuengling", 4.2, "American Lager", "new-brew-company");
        Beer actual = repository.addBeer(beer);
        assertNotNull(actual);
        assertEquals(5, actual.getBeerId());
        assertEquals(1, repository.findBeersByBrewery("new-brew-company").size());
    }

    @Test
    void shouldUpdateBeer(){
        Beer beer = new Beer("Yuengling", 4.2, "American Lager", "10-56-brewing-company-knox");
        beer.setBeerId(3);
        assertTrue(repository.updateBeer(beer));;
    }

    @Test
    void shouldDeleteBeer(){
        assertTrue(repository.deleteBeer(4));
    }

}