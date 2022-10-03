package dejabrew.domain;

import dejabrew.data.ReviewRepository;
import dejabrew.models.Review;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class ReviewServiceTest {

    @Autowired
    ReviewService service;

    @MockBean
    ReviewRepository repository;

    @Test
    void shouldAdd() {
        Review review = makeReview();
        Review mockOut = makeReview();
        mockOut.setReviewId(5);

        when(repository.add(review)).thenReturn(mockOut);

        Result<Review> actual = service.add(review);
        assertEquals(ResultType.SUCCESS, actual.getType());
        assertEquals(mockOut, actual.getPayload());
    }

    @Test
    void shouldNotUpdate() {
        Review review = makeReview();
        Result<Review> actual = service.update(review);
        assertEquals(ResultType.INVALID, actual.getType());

        review = makeReview();
        review.setReviewId(4);
        review.setBreweryId(null);
        actual = service.update(review);
        assertEquals(ResultType.INVALID, actual.getType());
    }

    @Test
    void shouldUpdate() {
        Review review= makeReview();
        review.setReviewId(3);
        review.setReview("Tester");

        when(repository.update(review)).thenReturn(true);

        Result<Review> actual = service.update(review);
        assertEquals(ResultType.SUCCESS, actual.getType());
    }

    Review makeReview() {
        Review review = new Review();
        review.setUserId(5);
        review.setBreweryId("10-56-brewing-company-knox");
        review.setRating(4);
        review.setReview("Test");
        return review;
    }

}