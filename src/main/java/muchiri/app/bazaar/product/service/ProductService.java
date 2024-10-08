package muchiri.app.bazaar.product.service;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.JdbiException;
import org.jdbi.v3.core.mapper.reflect.BeanMapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import muchiri.app.bazaar.product.ProductException;
import muchiri.app.bazaar.product.ProductNotExistException;
import muchiri.app.bazaar.product.model.Product;
import muchiri.app.bazaar.product.model.Status;

@ApplicationScoped
public class ProductService {
    @Inject
    private Jdbi jdbi;

    public List<Product> getListedAndActiveProduct(int page, int pageSize) {
        var query = """
                SELECT id, name, description, type, url, startingBid, auctionStartTime,
                auctionEndTime, status, isListed, pickupLocation FROM product
                WHERE isListed = TRUE AND status = :status ORDER BY id DESC
                LIMIT :limit OFFSET :offset
                """;
        int offset = (page - 1) * pageSize;
        return jdbi.withHandle(
                handle -> handle.select(query)
                        .bind("status", Status.ACTIVE)
                        .bind("limit", pageSize)
                        .bind("offset", offset)
                        .mapToBean(Product.class)
                        .list());
    }

    public List<Product> getPendingProducts(Long sellerId) {
        var queryBuilder = new StringBuilder(
                """
                        SELECT id, sellerId, name, description, type, url, startingBid,
                        auctionStartTime, auctionEndTime, status, isListed, pickupLocation
                        FROM product WHERE status = :status
                        """);
        if (sellerId != null) {
            queryBuilder.append(" AND sellerId = :sellerId");
        }
        var query = queryBuilder.toString();
        try (var handle = jdbi.open()) {
            var handleQuery = handle.createQuery(query).bind("status", Status.PENDNG);
            if (sellerId != null) {
                handleQuery.bind("sellerId", sellerId);
            }
            return handleQuery.mapToBean(Product.class).list();
        }
    }

    public void statusToPending(Long productId) {
        if (productId < 1) {
            throw new ProductNotExistException("product with id %d does not exist".formatted(productId));
        }
        var query = "UPDATE product SET status = ? WHERE id = ?";
        jdbi.useHandle(
                handle -> handle.execute(query, Status.PENDNG, productId));
    }

    public void listProduct(long id) {
        if (id < 1) {
            throw new ProductNotExistException("product with id %d does not exist".formatted(id));
        }
        var query = "UPDATE product SET isListed = TRUE, status = ? WHERE id = ?";
        jdbi.useHandle(
                handle -> {
                    var rows = handle.execute(query, Status.ACTIVE, id);
                    if (rows == 0) {
                        throw new ProductNotExistException("product with id %d does not exist".formatted(id));
                    }
                });
    }

    public void deleteProduct(long id) {
        if (id < 1) {
            throw new ProductNotExistException("product with id %d does not exist".formatted(id));
        }
        jdbi.useHandle(handle -> {
            var rows = handle.execute("DELETE FROM product WHERE id = ?", id);
            if (rows == 0) {
                throw new ProductNotExistException("product with id %d does not exist".formatted(id));
            }
        });
    }

    public void updateProduct(Product product) {
        var query = """
                UPDATE product SET name = :name, description = :description, type = :type,
                startingBid = :startingBid, auctionStartTime = :auctionStartTime,
                auctionEndTime = :auctionEndTime, pickupLocation = :pickupLocation
                WHERE id = :id
                """;
        jdbi.useHandle(
                handle -> handle.createUpdate(query).bindBean(product).execute());
    }

    public List<Product> getProductsBySellerId(long sellerId, int page, int pageSize) {
        var query = """
                SELECT id, name, description, type, url, startingBid, auctionStartTime,
                auctionEndTime, status, isListed, pickupLocation FROM product
                WHERE sellerId = :sellerId ORDER BY id DESC LIMIT :limit OFFSET :offset
                """;
        var offset = (page - 1) * pageSize;
        return jdbi.withHandle(
                handle -> handle.select(query)
                        .bind("sellerId", sellerId)
                        .bind("limit", pageSize)
                        .bind("offset", offset)
                        .mapToBean(Product.class)
                        .list());
    }

    public Optional<Product> getProductById(long id) {
        if (id < 1) {
            return Optional.empty();
        }
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
