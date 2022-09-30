package dejabrew.data;

import dejabrew.models.Review;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReviewJdbcTemplateRepositoryTest {

    final static int NEXT_REVIEW_ID = 5;
    @Autowired
    ReviewJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setUp() {
        knownGoodState.set();
    }

    @Test
    void shouldFindById() {
        Review actual = repository.findById(1);
        assertNotNull(actual);
    }

    @Test
    void shouldAdd(){
        Review review = makeReview();
        Review actual = repository.add(review);
        assertNotNull(review);
        assertEquals(NEXT_REVIEW_ID, actual.getReviewId());
    }

    @Test
    void shouldUpdate() {
        Review review = new Review();
        review.setReviewId(3);
        review.setBreweryId("10-56-brewing-company-knox");
        review.setRating(5);
        review.setReview("Updated Review");
        assertTrue(repository.update(review));
        review.setReviewId(16);
        review.setBreweryId("10-56-brewing-company-knox");
        review.setRating(5);
        review.setReview("Updated Review");
        assertFalse(repository.update(review));
    }

    @Test
    void shouldDelete() {
        assertTrue(repository.deleteById(4));
        assertFalse(repository.deleteById(4));
    }

    Review makeReview() {
        Review review = new Review();
        review.setUserId(2);
        review.setBreweryId("10-56-brewing-company-knox");
        review.setRating(4);
        review.setReview("Test");
        return review;
    }
}