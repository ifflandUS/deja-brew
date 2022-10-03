package dejabrew.data.mappers;

import dejabrew.models.Visit;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VisitMapper implements RowMapper<Visit> {
    public Visit mapRow(ResultSet resultSet, int i) throws SQLException {
        Visit visit = new Visit();
        visit.setVisitId(resultSet.getInt("visit_id"));
        visit.setUserId(resultSet.getInt("app_user_id"));
        visit.setBreweryId(resultSet.getString("brewery_id"));
        if (resultSet.getDate("visit_date") != null) {
            visit.setDate(resultSet.getDate("visit_date").toLocalDate());
        }
        return visit;
    }
}
