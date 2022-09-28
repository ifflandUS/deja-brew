package dejabrew.data;

import dejabrew.models.Beer;

import java.util.List;

public interface BeerRepository {

    List<Beer> getBeersByBrewery(String brewery_id);


    Beer addBeer(Beer beer);

    boolean updateBeer(Beer beer);

    boolean deleteBeer(int beerId);
}
