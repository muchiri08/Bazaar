package muchiri.app.bazaar.user.service;

import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import muchiri.app.bazaar.user.model.Bidder;
import muchiri.app.bazaar.user.model.Role;
import muchiri.app.bazaar.user.model.Seller;
import muchiri.app.bazaar.user.model.User;

@ApplicationScoped
public class UserService {
    @Inject
    private Sql2o sql2o;

    public Bidder newBidder(Bidder bidder) {
        bidder.setRole(Role.BIDDER);
        var query = """
                INSERT INTO appUser(name, email, passwordHash, phone, role, activated, createdAt)
                VALUES(:name, :email, :password, :phone, :role, FALSE, NOW());
                """;
        try (var conn = sql2o.open()) {
            var id = (long) conn.createQuery(query).bind(bidder).executeUpdate().getKey();
            bidder.setId(id);
            return bidder;
        } catch (Sql2oException e) {
            // Handle exceptions
            System.err.println(e);
            return null;
        }
    }

    public User newSeller(Seller seller) {
        seller.setRole(Role.SELLER);
        var query = """
                INSERT INTO appUser(name, email, passwordHash, phone, role, address, activated, createdAt)
                VALUES(:name, :email, :password, :phone, :role, :address, FALSE, NOW());
                """;
        try (var conn = sql2o.open()) {
            var id = (long) conn.createQuery(query).bind(seller).executeUpdate().getKey();
            seller.setId(id);
            return seller;
        } catch (Sql2oException e) {
            // Handle exceptions
            System.err.println(e);
            return null;
        }
    }
}
