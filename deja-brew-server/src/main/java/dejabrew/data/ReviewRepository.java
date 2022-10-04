package dejabrew.data;

import dejabrew.models.AppUser;
import dejabrew.models.Review;

import java.util.List;

public interface ReviewRepository {
    Review findById(int reviewId);

    List<Review> findByBrewery(String breweryId);

    List<Review> findByUser(AppUser appUser);

    Review add(Review review);

    boolean update(Review review);

    boolean deleteById(int reviewId);
}
