package dejabrew.models;

import dejabrew.App;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class AppUser extends User {

    private static String AUTHORITY_PREFIX = "ROLE_";

    private int appUserId;

    public AppUser(int appUserId, String username, String passwordHash, boolean disabled, List<String> roles) {
        super(username, passwordHash, !disabled, true, true, true, convertRolesToAuthorities(roles) );
        this.appUserId = appUserId;
    }


    public int getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(int appUserId) {
        this.appUserId = appUserId;
    }

    public static List<GrantedAuthority> convertRolesToAuthorities(List<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        for (String role : roles) {
            // verify it doesn't start with the prefix already
            Assert.isTrue(!role.startsWith(AUTHORITY_PREFIX), () -> String.format("Role %s already contains prefix %s", role, AUTHORITY_PREFIX));

            // add to the list
            authorities.add(new SimpleGrantedAuthority(AUTHORITY_PREFIX + role));
        }

        return authorities;
    }

    public static List<String> convertAuthoritiesToRoles(Collection<GrantedAuthority> authorities) {
        return authorities.stream()
                .map(auth -> auth.getAuthority().substring(AUTHORITY_PREFIX.length()))
                .collect(Collectors.toList());
    }
}
