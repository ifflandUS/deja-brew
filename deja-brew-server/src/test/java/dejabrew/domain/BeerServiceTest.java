package dejabrew.domain;

import dejabrew.data.BeerRepository;
import dejabrew.models.Beer;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class BeerServiceTest {

    @Autowired
    BeerService service;

    @MockBean
    BeerRepository repository;


    @Test
    void shouldNotAddNullBeer(){
        Result<Beer> result = service.addBeer(null);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotAddNullOrEmptyBeerName(){
        Beer beer = new Beer(null, 4.2, "American Lager", "new-brew-company");
        Result<Beer> result = service.addBeer(beer);
        assertEquals(ResultType.INVALID, result.getType());
        beer.setBeerName("");
        result = service.addBeer(beer);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotAddInvalidAbv(){
        Beer beer = new Beer("beer", -1.0, "American Lager", "new-brew-company");
        Result<Beer> result = service.addBeer(beer);
        assertEquals(ResultType.INVALID, result.getType());
        beer.setAbv(31.2);
        result = service.addBeer(beer);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotAddNullOrEmptyType(){
        Beer beer = new Beer("beer", 4.2, null, "new-brew-company");
        Result<Beer> result = service.addBeer(beer);
        assertEquals(ResultType.INVALID, result.getType());
        beer.setType("");
        result = service.addBeer(beer);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotAddNullOrEmptyBreweryId(){
        Beer beer = new Beer("beer", 4.2, "American Lager", null);
        Result<Beer> result = service.addBeer(beer);
        assertEquals(ResultType.INVALID, result.getType());
        beer.setBreweryId("");
        result = service.addBeer(beer);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotAddDuplicate(){
        List<Beer> beers = new ArrayList<>();
        beers.add(new Beer(1,"beer", 4.2, "American Lager", "cool-brewery-co" ));
        Beer beer = new Beer("beer", 4.2, "American Lager", "cool-brewery-co");
        Mockito.when(repository.findBeersByBrewery("cool-brewery-co"))
                .thenReturn(beers);
        Result<Beer> result = service.addBeer(beer);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldAddValidBeer(){
        Beer resultBeer = new Beer(1,"beer", 4.2, "American Lager", "cool-brewery-co" );
        Beer beer = new Beer("beer", 4.2, "American Lager", "cool-brewery-co");
        Mockito.when(repository.addBeer(any()))
                .thenReturn(resultBeer);
        Result<Beer> result = service.addBeer(beer);
        assertEquals(resultBeer, result.getPayload());
        assertEquals(ResultType.SUCCESS, result.getType());
    }
    @Test
    void shouldNotUpdateNullBeer(){
        Result<Beer> result = service.updateBeer(null);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotUpdateNullOrEmptyBeerName(){
        Beer beer = new Beer(1,null, 4.2, "American Lager", "new-brew-company");
        Result<Beer> result = service.updateBeer(beer);
        assertEquals(ResultType.INVALID, result.getType());
        beer.setBeerName("");
        result = service.updateBeer(beer);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotUpdateInvalidAbv(){
        Beer beer = new Beer(1,"beer", -1.0, "American Lager", "new-brew-company");
        Result<Beer> result = service.updateBeer(beer);
        assertEquals(ResultType.INVALID, result.getType());
        beer.setAbv(31.2);
        result = service.updateBeer(beer);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotUpdateNullOrEmptyType(){
        Beer beer = new Beer(1,"beer", 4.2, null, "new-brew-company");
        Result<Beer> result = service.updateBeer(beer);
        assertEquals(ResultType.INVALID, result.getType());
        beer.setType("");
        result = service.updateBeer(beer);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotUpdateNullOrEmptyBreweryId(){
        Beer beer = new Beer(1,"beer", 4.2, "American Lager", null);
        Result<Beer> result = service.updateBeer(beer);
        assertEquals(ResultType.INVALID, result.getType());
        beer.setBreweryId("");
        result = service.updateBeer(beer);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotUpdateDuplicate(){
        Beer beer = new Beer(1,"beer", 4.2, "American Lager", "cool-brewery-co");
        Mockito.when(repository.findBeersById(1))
                .thenReturn(beer);
        Result<Beer> result = service.updateBeer(beer);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotUpdateNotFound(){
        Beer beer = new Beer(100,"beer", 4.2, "American Lager", "cool-brewery-co");
        Mockito.when(repository.findBeersById(1))
                .thenReturn(null);
        Result<Beer> result = service.updateBeer(beer);
        assertEquals(ResultType.NOT_FOUND, result.getType());
    }

    @Test
    void shouldUpdateValidBeer(){
        Beer oldBeer = new Beer(1, "old", 4.2,"American Lager", "cool-brewery-co");
        Beer beer = new Beer(1,"beer", 4.2, "American Lager", "cool-brewery-co");
        Mockito.when(repository.findBeersById(1))
                .thenReturn(oldBeer);
        Mockito.when(repository.updateBeer(any()))
                .thenReturn(true);
        Result<Beer> result = service.updateBeer(beer);
        assertEquals(beer, result.getPayload());
        assertEquals(ResultType.SUCCESS, result.getType());
    }

    @Test
    void shouldNotDeleteNotFound(){
        Mockito.when(repository.deleteBeer(100))
                .thenReturn(false);
        Result<Beer> result = service.deleteBeer(100);
        assertEquals(ResultType.NOT_FOUND, result.getType());
    }

    @Test
    void shouldDeleteFound(){
        Mockito.when(repository.deleteBeer(1))
                .thenReturn(true);
        Result<Beer> result = service.deleteBeer(1);
        assertEquals(ResultType.SUCCESS, result.getType());
    }
}