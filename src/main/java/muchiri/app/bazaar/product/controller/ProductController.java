package muchiri.app.bazaar.product.controller;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import muchiri.app.bazaar.APIResponse;
import muchiri.app.bazaar.product.model.Product;
import muchiri.app.bazaar.product.model.Status;
import muchiri.app.bazaar.product.service.ProductService;

@Path("products")
@RequestScoped
public class ProductController {
    @Inject
    ProductService productService;

    @POST
    @Path("new")
    public Response createProduct(@Valid Product product) {
        product.setStatus(Status.INACTIVE);
        productService.newProduct(product);
        return Response.ok().entity(new APIResponse(200, "success")).build();
    }
}
