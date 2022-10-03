package dejabrew.data.mappers;

import dejabrew.models.Review;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReviewMapper implements RowMapper<Review> {
    public Review mapRow(ResultSet resultSet, int i) throws SQLException {
        Review review = new Review();
        review.setReviewId(resultSet.getInt("review_id"));
        review.setUserId(resultSet.getInt("user_id"));
        review.setBreweryId(resultSet.getString("brewery_id"));
        review.setRating(resultSet.getInt("rating"));
        review.setReview(resultSet.getString("review"));
        return review;
    }
}