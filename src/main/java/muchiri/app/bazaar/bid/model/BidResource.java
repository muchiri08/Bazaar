package muchiri.app.bazaar.bid.model;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;

public record BidResource(
        @NotNull(message = "bidder id required") Long bidderId,
        @NotNull(message = "product id required") Long productId,
        @NotNull(message = "bid amount is required") BigDecimal bidAmount) {
}
