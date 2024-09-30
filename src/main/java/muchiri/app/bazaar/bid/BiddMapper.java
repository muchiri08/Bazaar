package muchiri.app.bazaar.bid;

import muchiri.app.bazaar.bid.model.Bid;
import muchiri.app.bazaar.bid.model.BidResource;

public interface BiddMapper {
    static Bid toBid(BidResource r) {
        var bid = new Bid();
        bid.setBidderId(r.bidderId());
        bid.setProductId(r.productId());
        bid.setBidAmount(r.bidAmount());
        return bid;
    }
}
