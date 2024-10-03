package muchiri.app.bazaar;

import java.util.Set;

import java.time.Instant;
import java.time.Duration;

import io.smallrye.jwt.build.Jwt;
import muchiri.app.bazaar.user.model.UserDTO;

public interface JWTUtil {

    static String generateToken(UserDTO user, String issuer) {
        return Jwt.issuer(issuer)
                .upn(user.username())
                .groups(Set.of(user.role().name()))
                .claim("uid", user.id())
                .expiresAt(Instant.now().plus(Duration.ofDays(1)))
                .sign();
    }
}
