package dejabrew.data;

import dejabrew.models.AppUser;
import dejabrew.models.Visit;

import java.util.List;

public interface VisitRepository {
    Visit findById(int visitId);


    Visit findByBrewery(String breweryId);

    List<Visit> findByUser(AppUser user);

    Visit add(Visit visit);

    boolean update(Visit visit);

    boolean deleteById(int visitId);
}
