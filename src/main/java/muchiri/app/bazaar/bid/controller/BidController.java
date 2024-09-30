package muchiri.app.bazaar.bid.controller;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import muchiri.app.bazaar.APIResponse;
import muchiri.app.bazaar.bid.BiddMapper;
import muchiri.app.bazaar.bid.model.BidResource;
import muchiri.app.bazaar.bid.service.BidService;

@Path("market/items/bid")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
public class BidController {
    @Inject
    BidService bidService;

    @POST
    public Response neBid(@Valid BidResource bidResource) {
        var bid = BiddMapper.toBid(bidResource);
        bidService.newBid(bid);
        return Response.ok(new APIResponse(200, "success")).build();
    }
}
