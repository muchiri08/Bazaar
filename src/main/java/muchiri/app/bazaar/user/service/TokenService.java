package muchiri.app.bazaar.user.service;

import java.security.SecureRandom;
import java.util.Base64;

import org.jdbi.v3.core.Jdbi;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import muchiri.app.bazaar.user.TokenExpiredException;
import muchiri.app.bazaar.user.TokenNotFoudException;
import muchiri.app.bazaar.user.model.Token;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.Duration;

@Dependent
public class TokenService {

    @Inject
    private Jdbi jdbi;

    public long verifyToken(String plainToken) {
        var hash = hash(plainToken);
        var query = "SELECT userId, expiry FROM token WHERE hash = ?";
        try (var handle = jdbi.open()) {
            var tokenOptional = handle.select(query, hash)
                    .map((rs, ctx) -> new Token(
                            rs.getLong("userId"),
                            rs.getTimestamp("expiry").toInstant()))
                    .findOne();
            var token = tokenOptional.orElseThrow(
                    () -> new TokenNotFoudException("token does not exist"));
            if (Instant.now().isAfter(token.getExpiry())) {
                deleteToken(hash);
                throw new TokenExpiredException("token is expired");
            }
            deleteToken(hash);
            return token.getUserId();
        }
    }

    public void insert(Token token) {
        var query = "INSERT INTO token(hash, userId, expiry) VALUES (:hash, :userId, :expiry)";
        token.setExpiry(Instant.now().plus(Duration.ofHours(72)));
        jdbi.useHandle(handle -> {
            handle.createUpdate(query).bindBean(token).execute();
        });
    }

    public Token generateToken(long userId) {
        var token = new Token();
        token.setUserId(userId);

        var secureRandom = new SecureRandom();
        var randomBytes = new byte[16];
        secureRandom.nextBytes(randomBytes);
        var encoder = Base64.getUrlEncoder().withoutPadding();
        var plainToken = encoder.encodeToString(randomBytes);
        token.setPlain(plainToken);

        var hashedToken = hash(plainToken);
        token.setHash(hashedToken);
        return token;
    }

    private void deleteToken(byte[] hash) {
        var query = "DELETE FROM token WHERE hash = :hash";
        jdbi.useHandle(handle -> {
            handle.createUpdate(query).bind("hash", hash).execute();
        });
    }

    private byte[] hash(String token) {
        try {
            var md = MessageDigest.getInstance("SHA-256");
            return md.digest(token.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
