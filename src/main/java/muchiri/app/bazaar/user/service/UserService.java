package muchiri.app.bazaar.user.service;

import java.sql.SQLException;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.JdbiException;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import muchiri.app.bazaar.user.UserException;
import muchiri.app.bazaar.user.model.Bidder;
import muchiri.app.bazaar.user.model.Role;
import muchiri.app.bazaar.user.model.Seller;

@ApplicationScoped
public class UserService {
    @Inject
    private Jdbi jdbi;

    public void activateUser(long userId) {
        jdbi.useHandle(handle -> {
            handle.execute("UPDATE appUser SET activated = TRUE WHERE id = ?", userId);
        });
    }

    public Bidder newBidder(Bidder bidder) {
        bidder.setRole(Role.BIDDER);
        var query = """
                INSERT INTO appUser(username, name, email, passwordHash, phone, role, activated, createdAt)
                VALUES(:username, :name, :email, :password, :phone, :role, FALSE, NOW());
                """;
        try {
            jdbi.useHandle(handle -> {
                var id = handle.createUpdate(query).bindBean(bidder)
                        .executeAndReturnGeneratedKeys("id")
                        .mapTo(Long.class)
                        .first();
                bidder.setId(id);
            });
        } catch (JdbiException e) {
            if (e.getCause() instanceof SQLException x) {
                var message = x.getMessage();
                if (x.getSQLState().equals("23505")) {
                    handleUniqueConstraintViolation(message);
                }
            }
            throw new RuntimeException(e);
        }
        return bidder;
    }

    public Seller newSeller(Seller seller) {
        seller.setRole(Role.SELLER);
        var query = """
                INSERT INTO appUser(username, name, email, passwordHash, phone, role, address,
                activated, createdAt)
                VALUES(:username, :name, :email, :password, :phone, :role, :address, FALSE, NOW());
                """;
        try {
            jdbi.useHandle(handle -> {
                var id = handle.createUpdate(query).bindBean(seller)
                        .executeAndReturnGeneratedKeys("id")
                        .mapTo(Long.class)
                        .first();
                seller.setId(id);
            });
        } catch (JdbiException e) {
            if (e.getCause() instanceof SQLException x) {
                var message = x.getMessage();
                if (x.getSQLState().equals("23505")) {
                    handleUniqueConstraintViolation(message);
                }
            }
            throw new RuntimeException(e);
        }
        return seller;
    }

    private void handleUniqueConstraintViolation(String message) {
        if (message.contains("appuser_username_key")) {
            throw new UserException("username already exists");
        }
        if (message.contains("appuser_phone_key")) {
            throw new UserException("phone number already exists");
        }
        if (message.contains("appuser_email_key")) {
            throw new UserException("email already exists");
        }
    }
}
