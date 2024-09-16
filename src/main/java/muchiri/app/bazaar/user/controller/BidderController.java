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
import muchiri.app.bazaar.user.model.Bidder;
import muchiri.app.bazaar.user.service.UserService;

@Path("bidders")
@Produces(MediaType.APPLICATION_JSON)
public class BidderController {
    @Inject
    UserService userService;

    @POST
    @Path("new")
    public Response newBidder(@Valid Bidder bidder) {
        var newBidder = userService.newBidder(bidder);
        var status = Status.ACCEPTED.getStatusCode();
        var response = new APIResponse<Bidder>(status, "success");

        //TODO: Send Activation Email

        return Response.status(status).entity(response).build();
    }
}
