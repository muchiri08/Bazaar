package muchiri.app.bazaar.bid.service;

import java.sql.SQLException;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.JdbiException;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import muchiri.app.bazaar.bid.BidException;
import muchiri.app.bazaar.bid.model.Bid;

@ApplicationScoped
public class BidService {
    @Inject
    private Jdbi jdbi;

    public void newBid(Bid bid) {
        var query = """
                INSERT INTO bid(bidderId, productId, bidAmount, updatedAt, version)
                VALUES (:bidderId, :productId, :bidAmount, NOW(), :version)
                """;
        bid.setVersion((short) 1);
        try {
            jdbi.useHandle(handle -> handle.createUpdate(query).bindBean(bid).execute());
        } catch (JdbiException e) {
            if (e.getCause() instanceof SQLException ex) {
                if ("23503".equals(ex.getSQLState())) {
                    handleForeignContraintViolation(ex);
                }
            }
            throw new RuntimeException(e);
        }

    }

    private void handleForeignContraintViolation(Throwable e) {
        var message = e.getMessage();
        if (message.contains("bid_bidderid_fkey")) {
            throw new BidException("bidder does not exist");
        }
        if (message.contains("bid_productid_fkey")) {
            throw new BidException("product does not exist");
        }
    }
}
