package dejabrew.data;

import dejabrew.data.mappers.VisitMapper;
import dejabrew.models.Review;
import dejabrew.models.Visit;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class VisitJdbcTemplateRepository {
    private final JdbcTemplate jdbcTemplate;

    public VisitJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Visit findById(int visitId) {

        final String sql = "select visit_id, user_id, brewery_id, date "
                + "from visit "
                + "where visit_id = ?;";

        return jdbcTemplate.query(sql, new VisitMapper(), visitId).stream()
                .findFirst()
                .orElse(null);
    }

    public Review findByBrewery(Brewery brewery) {
        final String sql = "select brewery_id "
                + "from visit "
                + "where visit_id = ?;";

        return jdbcTemplate.query(sql, new VisitMapper(), brewery).stream()
                .findFirst()
                .orElse(null);
    }


    public Visit add(Visit visit) {

        final String sql = "insert into visit (user_id, brewery_id, date) "
                + " values (?,?,?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, visit.getUserId());
            ps.setInt(2, visit.getBreweryId());
            ps.setDate(3, visit.getDate() == null ? null : Date.valueOf(visit.getDate()));;

            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }
        visit.setUserId(keyHolder.getKey().intValue());
        return visit;
    }

    public boolean update(Visit visit) {

        final String sql = "update visit set "
                + "user_id = ?, "
                + "brewery_id = ?, "
                + "date"
                + "where visit_id = ?;";

        return jdbcTemplate.update(sql,
                visit.getUserId(),
                visit.getBreweryId(),
                visit.getDate(),
                visit.getVisitId()) > 0;
    }


    public boolean deleteById(int visitId) {
        return jdbcTemplate.update(
                "delete from visit where visit_id = ?", visitId) > 0;
    }

}
