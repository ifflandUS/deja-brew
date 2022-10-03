package dejabrew.data.mappers;

import dejabrew.models.VisitBeer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VisitBeerMapper implements RowMapper<VisitBeer> {
    public VisitBeer mapRow(ResultSet resultSet, int i) throws SQLException {
        VisitBeer visitBeer = new VisitBeer();
        visitBeer.setVisit_beer_id(resultSet.getInt("visit_beer_id"));
        visitBeer.setVisit_id(resultSet.getInt("visit_id"));
        visitBeer.setBeer_id(resultSet.getInt("beer_id"));
        return visitBeer;
    }

}
