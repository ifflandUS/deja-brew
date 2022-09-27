package dejabrew.data;

import dejabrew.models.AppUser;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class AppUserJdbcTemplateRepository implements AppUserRepository {

    private final JdbcTemplate template;

    public AppUserJdbcTemplateRepository(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public AppUser findByUsername(String name) {
        List<String> roles = findRolesByUsername(name);

        final String sql = "select app_user_id, username, password_hash, disabled " +
                "from app_user " +
                "where username = ?;";


        return template.query(sql, (rs, i) -> new AppUser(
                rs.getInt("app_user_id"),
                rs.getString("username"),
                rs.getString("password_hash"),
                rs.getBoolean("disabled"),
                roles
        ), name).stream().findFirst().orElse(null);
    }

    private List<String> findRolesByUsername(String name) {
        final String sql = "select ar.`name` " +
                "from app_user au " +
                "left join app_user_role aur on au.app_user_id = aur.app_user_id " +
                "left join app_role ar on ar.app_role_id = aur.app_role_id " +
                "where au.username = ?;";

        return template.query(sql, (rs, i) -> rs.getString("name"), name);

    }

    @Override
    public AppUser create(AppUser user) {
        final String sql = "insert into app_user (username, password_hash, disabled) values (?, ?, ?);";
        KeyHolder holder = new GeneratedKeyHolder(); // use consistent patterns

        int rowCount = template.update(conn -> {
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setBoolean(3, !user.isEnabled());

            return statement;
        }, holder);

        if (rowCount == 0) {
            return null;
        }

        user.setAppUserId(holder.getKey().intValue());

        updateRoles(user.getAppUserId(), AppUser.convertAuthoritiesToRoles(user.getAuthorities()));

        return user;
    }

    private void updateRoles(int appUserId, List<String> roles) {
        template.update("delete from app_user_role where app_user_id = ?", appUserId);

        if (roles == null) {
            return;
        }

        for (String role : roles) {
            template.update("insert into app_user_role " +
                    "select ?, app_role_id from app_role where `name` = ?;", appUserId, role);
        }
    }
}
