package dejabrew.data.Mappers;

import dejabrew.models.Visit;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VisitMapper implements RowMapper<Visit> {
    public Visit mapRow(ResultSet resultSet, int i) throws SQLException {
        Visit visit = new Visit();
        visit.setVisitId(resultSet.getInt("visit_id"));
        visit.setUserId(resultSet.getInt("user_id"));
        visit.setBreweryId(resultSet.getInt("brewery_id"));
        if (resultSet.getDate("date") != null) {
            visit.setDate(resultSet.getDate("date").toLocalDate());
        }
        return visit;
    }
}
