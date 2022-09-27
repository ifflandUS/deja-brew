package dejabrew.data;


import dejabrew.models.AppUser;

public interface AppUserRepository {

    AppUser findByUsername(String name);
    AppUser create(AppUser user);
}

