package muchiri.app.bazaar.user.model;

public record AuthResource(
        String username,
        String password,
        //Added here so that I can map to this bean during login verification
        Role role) {
}
