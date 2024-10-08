package muchiri.app.bazaar.product.controller;

import jakarta.annotation.security.DenyAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import muchiri.app.bazaar.APIResponse;
import muchiri.app.bazaar.product.ProductMapper;
import muchiri.app.bazaar.product.ProductNotExistException;
import muchiri.app.bazaar.product.model.Product;
import muchiri.app.bazaar.product.model.ProductResource;
import muchiri.app.bazaar.product.model.Status;
import muchiri.app.bazaar.product.service.ProductService;
import muchiri.app.bazaar.user.service.UserService;

@Path("products")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@DenyAll
public class ProductController {
    @Inject
    ProductService productService;
    @Inject
    UserService userService;

    @POST
    @Path("new")
    @RolesAllowed(value = { "SELLER" })
    public Response createProduct(@Valid Product product) {
        product.setStatus(Status.INACTIVE);
        productService.newProduct(product);
        return Response.ok().entity(new APIResponse(200, "success")).build();
    }

    @GET
    @Path("{id}")
    @RolesAllowed(value = { "SELLER" })
    public Response getProductById(@PathParam("id") Long id) {
        var product = productService.getProductById(id).orElseThrow(
                () -> new ProductNotExistException("product with id %d does not exist".formatted(id)));
        return Response.ok(product).build();
    }

    @GET
    @Path("seller/{sellerId}")
    @RolesAllowed(value = { "SELLER" })
    public Response getProductsForSeller(@PathParam("sellerId") Long sellerId,
            @QueryParam("page") @DefaultValue("1") int page,
            @QueryParam("pageSize") @DefaultValue("10") int pageSize) {
        var seller = userService.getSellerById(sellerId);
        if (seller.isEmpty()) {
            return Response.status(404)
                    .entity(new APIResponse(404, "seller with id %d does not exist".formatted(sellerId)))
                    .build();
        }
        var products = productService.getProductsBySellerId(sellerId, page, pageSize);
        return Response.ok().entity(products).build();
    }

    @PUT
    @Path("edit/{id}")
    @RolesAllowed(value = { "SELLER" })
    public Response updateProduct(@Valid ProductResource resource, @PathParam("id") Long id) {
        productService.getProductById(id).orElseThrow(
                () -> new ProductNotExistException("product with id %d does not exist".formatted(id)));
        var product = ProductMapper.toProduct(resource);
        product.setId(id);
        productService.updateProduct(product);
        return Response.ok().entity(new APIResponse(200, "success")).build();
    }

    @DELETE
    @Path("{id}")
    @RolesAllowed(value = { "SELLER" })
    public Response deleteProduct(@PathParam("id") Long id) {
        productService.deleteProduct(id);
        return Response.ok().entity(new APIResponse(200, "success")).build();
    }

    @PUT
    @Path("list/{id}")
    @RolesAllowed(value = { "SELLER" })
    public Response listProduct(@PathParam("id") Long id) {
        productService.listProduct(id);
        return Response.ok().entity(new APIResponse(200, "success")).build();
    }
}
