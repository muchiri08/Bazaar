package muchiri.app.bazaar;

import java.util.Set;

import java.time.Instant;
import java.time.Duration;

import io.smallrye.jwt.build.Jwt;

public interface JWTUtil {

    static String generateToken(String username, String role, String issuer) {
        return Jwt.issuer(issuer)
                .upn(username)
                .groups(Set.of(role))
                .expiresAt(Instant.now().plus(Duration.ofDays(1)))
                .sign();
    }
}
