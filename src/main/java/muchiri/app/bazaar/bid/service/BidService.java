package muchiri.app.bazaar.bid.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.JdbiException;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import muchiri.app.bazaar.bid.BidException;
import muchiri.app.bazaar.bid.model.Bid;
import muchiri.app.bazaar.bid.model.BidDTO;

@ApplicationScoped
public class BidService {
    @Inject
    private Jdbi jdbi;

    public List<BidDTO> getBidsForProduct(long productId) {
        var query = """
                SELECT bid.id, appUser.username, bid.bidAmount, bid.updatedAt FROM bid
                JOIN appUser ON bid.bidderId = appUser.id
                WHERE productId = :productId ORDER BY id DESC;
                """;
        return jdbi.withHandle(
                handle -> handle.createQuery(query)
                        .bind("productId", productId)
                        .map((rs, ctx) -> new BidDTO(
                                rs.getLong("id"),
                                rs.getString("username"), rs.getBigDecimal("bidAmount"),
                                rs.getTimestamp("updatedAt").toInstant()))
                        .list());
    }

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

    public boolean rebid(long id, BigDecimal bidAmount) {
        var query = """
                UPDATE bid SET bidAmount = ?, updatedAt = NOW(), version = version + 1 WHERE id = ?
                """;
        var success = new AtomicBoolean(false);
        jdbi.useHandle(handle -> {
            var rows = handle.execute(query, bidAmount, id);
            if (rows == 1) {
                success.set(true);
            }
        });
        return success.get();
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
