package muchiri.app.bazaar.user.model;

public record UserDTO(
        long id,
        String username,
        String passwordHash,
        Role role) {
}
