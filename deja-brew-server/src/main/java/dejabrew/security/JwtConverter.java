package dejabrew.security;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtConverter {

    private final String ISSUER = "deja-brew-app";
    private final String AUTHORITIES = "authorities";
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private final int EXPIRATION_MINUTES = 15;
    private final int EXPIRATION_TIME = EXPIRATION_MINUTES * 60 * 1000; // nanosecond conversion


    public User getUserFromToken(String token) {
        try {
            Jws<Claims> jws = Jwts.parserBuilder()
                    .requireIssuer(ISSUER)
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);

            String username = jws.getBody().getSubject();

            List<GrantedAuthority> authorities = Arrays.stream(((String) jws.getBody().get(AUTHORITIES)).split(","))
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            return new User(username, username, authorities);

        } catch (JwtException ex) {
            System.out.println(ex.getMessage());
        }

        return null;
    }

    //    * [ ] `getTokenFromUser`
    public String getTokenFromUser(User user) {
        // get the rolls off of the user
        String roles = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));

        return Jwts.builder()
                .setIssuer(ISSUER)
                .setSubject(user.getUsername()) // this is typically what the actual user name or id

                .claim(AUTHORITIES, roles) // claiming to be true about the person that the token is about

                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }
}
