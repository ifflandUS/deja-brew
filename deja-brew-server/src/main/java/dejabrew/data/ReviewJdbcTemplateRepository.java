package dejabrew.data;


import dejabrew.data.mappers.ReviewMapper;
import dejabrew.models.AppUser;
import dejabrew.models.Review;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class ReviewJdbcTemplateRepository implements ReviewRepository {
    private final JdbcTemplate jdbcTemplate;

    public ReviewJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Review findById(int reviewId) {

        final String sql = "select review_id, app_user_id, brewery_id, rating, review "
                + "from review "
                + "where review_id = ?;";

        return jdbcTemplate.query(sql, new ReviewMapper(), reviewId).stream()
                .findFirst()
                .orElse(null);
    }


    @Override
    public Review findByBrewery(String breweryId) {
        final String sql = "select *"
                + "from review "
                + "where brewery_id = ?;";

        return jdbcTemplate.query(sql, new ReviewMapper(), breweryId).stream()
                .findFirst()
                .orElse(null);
    }


    @Override
    public Review findByUser(AppUser appUser) {
        final String sql = "select *"
                + "from review "
                + "where app_user_id = ?;";

        return jdbcTemplate.query(sql, new ReviewMapper(), appUser.getAppUserId()).stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public Review add(Review review) {

        final String sql = "insert into review (app_user_id, brewery_id, rating, review) "
                + " values (?,?,?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, review.getUserId());
            ps.setString(2, review.getBreweryId());
            ps.setInt(3, review.getRating());
            ps.setString(4, review.getReview());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }
        review.setReviewId(keyHolder.getKey().intValue());
        return review;
    }


    @Override
    public boolean update(Review review) {

        final String sql = "update review set "
                + "brewery_id = ?, "
                + "rating = ?, "
                + "review = ? "
                + "where review_id = ?;";

        return jdbcTemplate.update(sql,
                review.getBreweryId(),
                review.getRating(),
                review.getReview(),
                review.getReviewId()) > 0;
    }


    @Override
    public boolean deleteById(int reviewId) {
        return jdbcTemplate.update(
                "delete from review where review_id = ?", reviewId) > 0;
    }

}