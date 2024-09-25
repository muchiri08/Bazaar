package muchiri.app.bazaar.product.model;

import java.math.BigDecimal;
import java.time.Instant;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductResource(
        @NotBlank(message = "name is required") String name,
        String description,
        @NotBlank(message = "type is required") String type,
        @NotNull(message = "startingBid is required") BigDecimal startingBid,
        @NotNull(message = "auctionStartTime is required") Instant auctionStartTime,
        @NotNull(message = "auctionEndTime is required") Instant auctionEndTime,
        @NotBlank(message = "pickupLocation is required") String pickupLocation) {
}
