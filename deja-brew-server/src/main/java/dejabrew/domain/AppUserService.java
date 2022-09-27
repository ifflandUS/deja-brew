package dejabrew.domain;

import dejabrew.data.AppUserRepository;
import dejabrew.models.AppUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.List;

@Service
public class AppUserService implements UserDetailsService {

    private final AppUserRepository repo;
    private final PasswordEncoder encoder;

    public AppUserService(AppUserRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user =  repo.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with name %s", username));
        }

        return user;
    }

    public AppUser create(String username, String password) {
        // validate the username
        validateUsername(username);

        // validate the password
        validatePassword(password);

        // hash the password so we don't try to save a password directly into the database
        String passwordHash = encoder.encode(password);

        // save the user
        return repo.create(new AppUser(0, username, passwordHash, false, List.of("USER")));
    }

    private void validateUsername(String username) {
        if (username == null || username.isBlank()) {
            throw new ValidationException("Username cannot be blank");
        }

        if (username.length() > 50) {
            throw new ValidationException("Username cannot be longer than 50 characters");
        }
    }

    private void validatePassword(String password) {

        if (password == null || password.length() < 8) {
            throw new ValidationException("password must be at least 8 characters long");
        }

        int digits = 0;
        int chars = 0;
        int other = 0;

        // this is an old school way to check a password, but this kind of style had fallen out of favor
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                digits++;
            } else if (Character.isLetter(c)) {
                chars++;
            } else {
                other++;
            }
        }

        if (digits == 0 || chars == 0 || other == 0) {
            throw new ValidationException("Password must contain a combination of at least one digit, letter, and special character");
        }

    }

}
