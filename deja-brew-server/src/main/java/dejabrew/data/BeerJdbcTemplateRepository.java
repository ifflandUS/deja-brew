package dejabrew.data;

import dejabrew.data.mappers.BeerMapper;
import dejabrew.models.Beer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class BeerJdbcTemplateRepository implements BeerRepository{

    private final JdbcTemplate jdbcTemplate;

    public BeerJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Beer> findBeersByBrewery(String breweryId) {
        final String sql = "select beer_id, beer_name, abv, type, brewery_id " +
                "from beer " +
                "where brewery_id = ?;";

        return jdbcTemplate.query(sql, new BeerMapper(), breweryId);
    }

    @Override
    public Beer findBeersById(int beerId){
        final String sql = "select beer_id, beer_name, abv, type, brewery_id " +
                "from beer " +
                "where beer_id = ?;";
        return jdbcTemplate.queryForObject(sql, new BeerMapper(), beerId);
    }
    @Override
    public Beer addBeer(Beer beer) {
        final String sql = "insert into beer (beer_name, abv, type, brewery_id) "
                + " values (?,?,?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, beer.getBeerName());
            ps.setDouble(2,beer.getAbv());
            ps.setString(3, beer.getType());
            ps.setString(4, beer.getBreweryId());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }
        beer.setBeerId(keyHolder.getKey().intValue());
        return beer;
    }

    @Override
    public boolean updateBeer(Beer beer) {
        final String sql = "update beer set "
                + "beer_name = ?, "
                + "abv = ?, "
                + "type = ?, "
                + "brewery_id = ?"
                + "where beer_id = ?;";

        return jdbcTemplate.update(sql,
                beer.getBeerName(),
                beer.getAbv(),
                beer.getType(),
                beer.getBreweryId(),
                beer.getBeerId()) > 0;
    }

    @Override
    public boolean deleteBeer(int beerId) {
        final String sql = "delete from beer where beer_id = ?";

        int rowCount = jdbcTemplate.update(sql, beerId);
        return rowCount > 0;
    }
}
