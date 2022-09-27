package dejabrew.controllers;

import dejabrew.domain.AppUserService;
import dejabrew.models.AppUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import dejabrew.security.JwtConverter;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {

    private final AuthenticationManager manager;
    private final JwtConverter converter;
    private final AppUserService service;

    public AuthController(AuthenticationManager manager, JwtConverter converter, AppUserService service) {
        this.manager = manager;
        this.converter = converter;
        this.service = service;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authentication(@RequestBody Map<String, String> credentials) {

        String username = credentials.get("username");
        String password = credentials.get("password");

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);

        var authentication = manager.authenticate(token);
        if (authentication.isAuthenticated()) {
            User user = (User) authentication.getPrincipal();

            String jwtToken = converter.getTokenFromUser(user);

            Map<String, String> returnMap = new HashMap<>();
            returnMap.put("jwt_token", jwtToken);
            return new ResponseEntity<>(returnMap, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PostMapping("/create_account")
    public ResponseEntity<?> createAccount(@RequestBody Map<String, String> credentials) {

        String username = credentials.get("username");
        String password = credentials.get("password");

        AppUser user = service.create(username, password);

        Map<String, Integer> returnMap = new HashMap<>();
        returnMap.put("appUserId", user.getAppUserId());


        return new ResponseEntity<>(returnMap, HttpStatus.CREATED);
    }
}
