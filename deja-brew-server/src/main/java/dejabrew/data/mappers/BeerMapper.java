package dejabrew.data.mappers;

import dejabrew.models.Beer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BeerMapper implements RowMapper<Beer> {
    public Beer mapRow(ResultSet resultSet, int i) throws SQLException {
        Beer beer = new Beer();
        beer.setBeerId(resultSet.getInt("beer_id"));
        beer.setBeerName(resultSet.getString("beer_name"));
        beer.setAbv(resultSet.getDouble("abv"));
        beer.setType(resultSet.getString("type"));
        beer.setBreweryId(resultSet.getString("brewery_id"));
        return beer;
    }
}
