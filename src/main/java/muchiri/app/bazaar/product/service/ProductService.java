package muchiri.app.bazaar.product.service;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.JdbiException;
import org.jdbi.v3.core.mapper.reflect.BeanMapper;

import java.sql.SQLException;
import java.util.Optional;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import muchiri.app.bazaar.product.ProductException;
import muchiri.app.bazaar.product.model.Product;

@ApplicationScoped
public class ProductService {
    @Inject
    private Jdbi jdbi;

    public Optional<Product> getProductById(long id) {
        var query = """
                SELECT id, sellerId, name, description, type, url, startingBid,
                auctionStartTime, auctionEndTime, status, isListed, pickupLocation
                FROM product WHERE id = :id
                """;
        return jdbi.withHandle(
                handle -> handle.createQuery(query).bind("id", id).map(BeanMapper.of(Product.class)).findOne());
    }

    public void newProduct(Product product) {
        var query = """
                INSERT INTO product(sellerId, name, description, type, url, startingBid,
                auctionStartTime, auctionEndTime, status, isListed, pickupLocation)
                VALUES (:sellerId, :name, :description, :type, :url, :startingBid,
                :auctionStartTime, :auctionEndTime, :status, :isListed, :pickupLocation)
                """;
        try {
            jdbi.useHandle(handle -> {
                handle.createUpdate(query).bindBean(product).execute();
            });
        } catch (JdbiException e) {
            if (e.getCause() instanceof SQLException ex) {
                if ("23503".equals(ex.getSQLState())) {
                    System.out.println("here");
                    throw new ProductException("seller with id %d does not exist".formatted(product.getSellerId()));
                }
            }
            throw new RuntimeException(e);
        }
    }
}
