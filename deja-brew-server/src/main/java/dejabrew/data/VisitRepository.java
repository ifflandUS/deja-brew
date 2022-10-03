package dejabrew.data;

import dejabrew.models.Visit;

public interface VisitRepository {
    Visit findById(int visitId);

    Visit findByBrewery(String breweryId);

    Visit add(Visit visit);

    boolean update(Visit visit);

    boolean deleteById(int visitId);
}
