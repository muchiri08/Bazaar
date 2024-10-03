package muchiri.app.bazaar.product.controller;

import jakarta.annotation.security.DenyAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import muchiri.app.bazaar.APIResponse;
import muchiri.app.bazaar.product.service.ProductService;

@Path("market/items")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@DenyAll
public class MarketController {
    @Inject
    ProductService productService;

    @GET
    @RolesAllowed({"SELLER", "BIDDER"})
    public Response getListedAndActiveProducts(@QueryParam("page") @DefaultValue("1") int page,
            @QueryParam("pageSize") @DefaultValue("20") int pageSize) {
        var products = productService.getListedAndActiveProduct(page, pageSize);
        return Response.ok(products).build();
    }

    @POST
    @Path("{id}")
    @RolesAllowed({"BIDDER"})
    public Response productStatusToPending(@PathParam("id") Long productId) {
        productService.statusToPending(productId);
        return Response.ok(new APIResponse(200, "success")).build();
    }

    @GET
    @Path("pending")
    @RolesAllowed({"SELLER", "BIDDER"})
    public Response getPendingProduct(@QueryParam("sellerId") Long sellerId) {
        var products = productService.getPendingProducts(sellerId);
        return Response.ok(products).build();
    }
}
