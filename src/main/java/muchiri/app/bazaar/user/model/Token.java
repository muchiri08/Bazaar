package muchiri.app.bazaar.user.model;

import java.time.Instant;

public class Token {
    private long userId;
    private String plain;
    private byte[] hash;
    private Instant expiry;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getPlain() {
        return plain;
    }

    public void setPlain(String plainToken) {
        this.plain = plainToken;
    }

    public byte[] getHash() {
        return hash;
    }

    public void setHash(byte[] hashToken) {
        this.hash = hashToken;
    }

    public Instant getExpiry() {
        return expiry;
    }

    public void setExpiry(Instant expiry) {
        this.expiry = expiry;
    }

}
