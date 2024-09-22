package muchiri.app.bazaar.user;

public class TokenNotFoudException extends RuntimeException {
    public TokenNotFoudException(String message) {
        super(message);
    }
}
