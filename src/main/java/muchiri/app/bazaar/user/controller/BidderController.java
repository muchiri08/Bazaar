package muchiri.app.bazaar.user.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import muchiri.app.bazaar.user.model.Bidder;
import muchiri.app.bazaar.user.service.UserService;

@Path("bidders")
public class BidderController {
    @Inject
    UserService userService;

    @POST
    @Path("new")
    public Response newBidder(Bidder bidder) {
        var newBidder = userService.newBidder(bidder);
        return Response.status(Status.CREATED).entity(newBidder).build();
    }
}
