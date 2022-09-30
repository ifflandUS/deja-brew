package dejabrew.data;

import dejabrew.models.VisitBeer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class VisitBeerJdbcTemplateRepository implements VisitBeerRepository {
    private final JdbcTemplate jdbcTemplate;

    public VisitBeerJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public boolean add(VisitBeer visitBeer) {

        final String sql = "insert into visit_beer (visit_id, beer_id) "
                + "values "
                + "(?,?);";

        return jdbcTemplate.update(sql,
                visitBeer.getVisit_id(),
                visitBeer.getBeer_id()) > 0;
    }


    @Override
    public boolean update(VisitBeer visitBeer) {

        final String sql = "update visit_beer set "
                + "where visit_id = ? and beer_id = ?;";

        return jdbcTemplate.update(sql,
                visitBeer.getVisit_id(),
                visitBeer.getBeer_id(),
                visitBeer.getVisit_beer_id()) > 0;

    }


    @Override
    public boolean deleteByKey(int visitId, int beerId) {

        final String sql = "delete from visit_beer "
                + "where visit_id = ? and beer_id = ?;";

        return jdbcTemplate.update(sql, visitId, beerId) > 0;
    }
}
