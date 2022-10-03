package dejabrew.data;

import dejabrew.models.AppUser;
import dejabrew.models.Review;

public interface ReviewRepository {
    Review findById(int reviewId);

    Review findByBrewery(String breweryId);

    Review findByUser(AppUser user);

    Review add(Review review);

    boolean update(Review review);

    boolean deleteById(int reviewId);
}
