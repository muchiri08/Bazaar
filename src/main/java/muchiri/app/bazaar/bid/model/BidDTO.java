package muchiri.app.bazaar.bid.model;

import java.math.BigDecimal;
import java.time.Instant;

public record BidDTO(
        long id,
        String username,
        BigDecimal bidAmount,
        Instant updatedAt) {
}
