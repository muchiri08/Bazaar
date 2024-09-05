package muchiri.app.bazaar.user.service;

import org.sql2o.Sql2o;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import muchiri.app.bazaar.user.model.Bidder;
import muchiri.app.bazaar.user.model.Role;
import muchiri.app.bazaar.user.model.User;

@ApplicationScoped
public class UserService {
    @Inject
    private Sql2o sql2o;

    public Bidder newBidder(Bidder bidder) {
        bidder.setRole(Role.BIDDER);
        return (Bidder) newUser(bidder);
    }

    private User newUser(User user) {
        var query = """
                INSERT INTO appUser(name, email, passwordHash, phone, role, address, activated, createdAt)
                VALUES(:name, :email, :password, :phone, :role, :address, FALSE, NOW());
                """;
        try (var conn = sql2o.open()) {
            var id = (long) conn.createQuery(query).bind(user).executeUpdate().getKey();
            user.setId(id);
            return user;
        }
    }
}
