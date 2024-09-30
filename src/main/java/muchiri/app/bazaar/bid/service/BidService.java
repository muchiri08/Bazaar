package muchiri.app.bazaar.bid.service;

import org.jdbi.v3.core.Jdbi;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
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
        jdbi.useHandle(handle -> handle.createUpdate(query).bindBean(bid).execute());
    }
}
