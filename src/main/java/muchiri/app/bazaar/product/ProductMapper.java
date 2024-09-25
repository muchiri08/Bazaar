package muchiri.app.bazaar.product;

import muchiri.app.bazaar.product.model.Product;
import muchiri.app.bazaar.product.model.ProductResource;

public interface ProductMapper {
    static Product toProduct(ProductResource p) {
        var product = new Product();
        product.setName(p.name());
        product.setDescription(p.description());
        product.setType(p.type());
        product.setStartingBid(p.startingBid());
        product.setAuctionStartTime(p.auctionStartTime());
        product.setAuctionEndTime(p.auctionEndTime());
        product.setPickupLocation(p.pickupLocation());
        return product;
    }
}
