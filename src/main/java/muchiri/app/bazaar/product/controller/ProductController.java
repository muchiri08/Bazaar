package muchiri.app.bazaar.product.controller;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import muchiri.app.bazaar.APIResponse;
import muchiri.app.bazaar.product.model.Product;
import muchiri.app.bazaar.product.model.Status;
import muchiri.app.bazaar.product.service.ProductService;
import muchiri.app.bazaar.user.service.UserService;

@Path("products")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
public class ProductController {
    @Inject
    ProductService productService;
    @Inject
    UserService userService;

    @POST
    @Path("new")
    public Response createProduct(@Valid Product product) {
        product.setStatus(Status.INACTIVE);
        productService.newProduct(product);
        return Response.ok().entity(new APIResponse(200, "success")).build();
    }

    @GET
    @Path("{id}")
    public Response getProductById(@PathParam("id") Long id) {
        var product = productService.getProductById(id).orElseThrow(
                () -> new WebApplicationException(
                        Response.status(400)
                                .entity(new APIResponse(400, "product with id %d does not exist".formatted(id)))
                                .build()));
        return Response.ok(product).build();
    }

    @GET
    @Path("seller/{sellerId}")
    public Response getProductsForSeller(@PathParam("sellerId") Long sellerId,
            @QueryParam("page") @DefaultValue("1") int page,
            @QueryParam("pageSize") @DefaultValue("10") int pageSize) {
        var seller = userService.getSellerById(sellerId);
        if (seller.isEmpty()) {
            throw new WebApplicationException(
                    Response.status(404)
                            .entity(new APIResponse(404, "seller with id %d does not exist".formatted(sellerId)))
                            .build());
        }
        var products = productService.getProductsBySellerId(sellerId, page, pageSize);
        return Response.ok().entity(products).build();
    }
}
