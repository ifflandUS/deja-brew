package dejabrew.config;


import dejabrew.models.AppUser;
import dejabrew.security.JwtConverter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Component
public class JwtStringToUserConverter implements Converter<String, AppUser> {

    JwtConverter converter;

    public JwtStringToUserConverter(JwtConverter converter){
        this.converter = converter;
    }

    @Override
    public AppUser convert(String s) {
        return converter.getAppUserFromToken(s.substring("Bearer ".length()));
    }
}
