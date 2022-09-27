package dejabrew.data;

import dejabrew.models.Review;

public interface ReviewRepository {
    Review findById(int reviewId);

    Review findByBrewery(Brewery brewery);

    Review findByUser(User user);

    Review add(Review review);

    boolean update(Review review);

    boolean deleteById(int reviewId);
}
