package dejabrew.data;

import dejabrew.models.Beer;

import java.util.List;

public interface BeerRepository {

    List<Beer> findBeersByBrewery(String brewery_id);

    Beer findBeersById(int beerId);
    Beer addBeer(Beer beer);

    boolean updateBeer(Beer beer);

    boolean deleteBeer(int beerId);
}
