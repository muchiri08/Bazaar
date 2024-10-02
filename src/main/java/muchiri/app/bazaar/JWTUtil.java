package muchiri.app.bazaar;

import java.util.Set;

import java.time.Instant;
import java.time.Duration;

import io.smallrye.jwt.build.Jwt;
import muchiri.app.bazaar.user.model.Role;

public interface JWTUtil {

    static String generateToken(String username, Role role, String issuer) {
        return Jwt.issuer(issuer)
                .upn(username)
                .groups(Set.of(role.name()))
                .expiresAt(Instant.now().plus(Duration.ofDays(1)))
                .sign();
    }
}
