package dejabrew.data;

import dejabrew.models.VisitBeer;

public interface VisitBeerRepository {
    boolean add(VisitBeer visitBeer);
    public boolean add(int visit_id, int beer_id);

    boolean update(VisitBeer visitBeer);

    boolean deleteById(int visitBeerId);
}
