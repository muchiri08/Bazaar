package muchiri.app.bazaar.user.controller;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import muchiri.app.bazaar.APIResponse;
import muchiri.app.bazaar.user.model.Seller;
import muchiri.app.bazaar.user.service.UserService;

@Path("sellers")
@Produces(MediaType.APPLICATION_JSON)
public class SellerController {
    @Inject
    UserService userService;

    @POST
    @Path("new")
    public Response newSeller(@Valid Seller seller) {
        var newSeller = userService.newSeller(seller);
        var status = Status.ACCEPTED.getStatusCode();
        var response = new APIResponse(status, "success");

        // TODO: Send Activation Email

        return Response.status(status).entity(response).build();
    }
}
