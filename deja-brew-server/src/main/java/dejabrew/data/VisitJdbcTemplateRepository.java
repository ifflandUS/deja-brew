package dejabrew.data;

import dejabrew.data.mappers.BeerMapper;
import dejabrew.data.mappers.VisitMapper;
import dejabrew.models.AppUser;
import dejabrew.models.Beer;
import dejabrew.models.Visit;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class VisitJdbcTemplateRepository implements VisitRepository {
    private final JdbcTemplate jdbcTemplate;
    private final VisitBeerRepository vbRepo;

    public VisitJdbcTemplateRepository(JdbcTemplate jdbcTemplate, VisitBeerRepository vbRepo) {
        this.jdbcTemplate = jdbcTemplate;
        this.vbRepo = vbRepo;
    }

    @Override
    public Visit findById(int visitId) {

        final String sql = "select visit_id, app_user_id, brewery_id, visit_date "
                + "from visit "
                + "where visit_id = ?;";

        return jdbcTemplate.query(sql, new VisitMapper(), visitId).stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Visit> findByUser(AppUser user) {
        final String sql = "select * "
                + "from visit "
                + "where app_user_id = ?;";

        List<Visit> visits = jdbcTemplate.query(sql, new VisitMapper(),user.getAppUserId());
        for (Visit visit : visits){
            setVisitBeers(visit);
        }

        return visits;

    }


    @Override
    public Visit add(Visit visit) {

        final String sql = "insert into visit (app_user_id, brewery_id, visit_date) "
                + "values (?,?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, visit.getUserId());
            ps.setString(2, visit.getBreweryId());
            ps.setDate(3, visit.getDate() == null ? null : Date.valueOf(visit.getDate()));
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }


        for (Beer beer : visit.getBeerList()) {
            vbRepo.add(keyHolder.getKey().intValue(), beer.getBeerId());
        }

        visit.setVisitId(keyHolder.getKey().intValue());
        return visit;
    }

    @Override
    public boolean update(Visit visit) {

        final String sql = "update visit set "
                + "brewery_id = ?, "
                + "visit_date = ? "
                + "where visit_id = ?;";

        return jdbcTemplate.update(sql,
                visit.getBreweryId(),
                visit.getDate(),
                visit.getVisitId()) > 0;
    }


    @Override
    public boolean deleteById(int visitId) {
        return jdbcTemplate.update(
                "delete from visit where visit_id = ?", visitId) > 0;
    }

    private void setVisitBeers(Visit visit){
        final String sql = "select b.beer_id, beer_name, abv, `type`, brewery_id from beer b"
        + " join visit_beer v on v.beer_id = b.beer_id"
        + " where v.visit_id = ?;";

         visit.setBeerList(jdbcTemplate.query(sql, new BeerMapper(), visit.getVisitId()));
    }



}
