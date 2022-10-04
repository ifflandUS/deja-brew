package dejabrew.domain;

import dejabrew.data.ReviewRepository;
import dejabrew.models.AppUser;
import dejabrew.models.Review;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    private final ReviewRepository repository;


    public ReviewService(ReviewRepository repository) {
        this.repository = repository;
    }

    public Review findById(int reviewId) {
        return repository.findById(reviewId);
    }

    public List<Review> findByBrewery(String breweryId) {
        return repository.findByBrewery(breweryId);
    }

    public List<Review> findByUser(AppUser appUser) {
        return repository.findByUser(appUser);
    }

    public Result<Review> add(Review review) {
        Result<Review> result = validate(review);
        if (!result.isSuccess()) {
            return result;
        }

        if (review.getReviewId() != 0) {
            result.addMessage("reviewId cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }

        review = repository.add(review);
        result.setPayload(review);
        return result;
    }

    public Result<Review> update(Review review) {
        Result<Review> result = validate(review);
        if (!result.isSuccess()) {
            return result;
        }

        if (review.getReviewId() <= 0) {
            result.addMessage("reviewId must be set for `update` operation", ResultType.INVALID);
            return result;
        }

        if (!repository.update(review)) {
            String msg = String.format("reviewId: %s, not found", review.getReviewId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    public boolean deleteById(int reviewId) {
        return repository.deleteById(reviewId);
    }

    private Result<Review> validate(Review review) {
        Result<Review> result = new Result<>();
        if (review == null) {
            result.addMessage("review cannot be null", ResultType.INVALID);
            return result;
        }
        if (review.getBreweryId() == null) {
            result.addMessage("brewery is required", ResultType.INVALID);
        }
        if (review.getUserId() == 0) {
            result.addMessage("user is required", ResultType.INVALID);
        }
        if (review.getRating() < 1 || review.getRating() > 5) {
            result.addMessage("rating must be between the number 1 and 5", ResultType.INVALID);
        }

//        if () {
//
//            throw new DataIntegrityViolationException("Brewery cannot be duplicated. The breweryId differentiates between duplicate names.");
//        }
        return result;
    }
}
