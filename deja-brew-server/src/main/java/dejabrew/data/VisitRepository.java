package dejabrew.data;

import dejabrew.models.Visit;

public interface VisitRepository {
    Visit findById(int visitId);

//    Review findByBrewery(Brewery brewery);

    Visit add(Visit visit);

    boolean update(Visit visit);

    boolean deleteById(int visitId);
}
