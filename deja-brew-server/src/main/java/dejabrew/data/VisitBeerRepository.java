package dejabrew.data;

import dejabrew.models.VisitBeer;

public interface VisitBeerRepository {
    boolean add(VisitBeer visitBeer);

    boolean update(VisitBeer visitBeer);

    boolean deleteByKey(int visitId, int beerId);
}
