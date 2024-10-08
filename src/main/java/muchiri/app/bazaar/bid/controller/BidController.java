package muchiri.app.bazaar.bid.controller;

import java.math.BigDecimal;

import jakarta.annotation.security.DenyAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import muchiri.app.bazaar.APIResponse;
import muchiri.app.bazaar.bid.BiddMapper;
import muchiri.app.bazaar.bid.model.BidResource;
import muchiri.app.bazaar.bid.service.BidService;

@Path("market/items/bid")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@DenyAll
public class BidController {
    @Inject
    BidService bidService;

    @GET
    @Path("{id}")
    @RolesAllowed(value = {"BIDDER", "SELLER"})
    public Response bidsForProduct(@PathParam("id") Long productId) {
        var bids = bidService.getBidsForProduct(productId);
        return Response.ok(bids).build();
    }

    @POST
    @RolesAllowed(value = {"BIDDER"})
    public Response newBid(@Valid BidResource bidResource) {
        var bid = BiddMapper.toBid(bidResource);
        bidService.newBid(bid);
        return Response.ok(new APIResponse(200, "success")).build();
    }

    @POST
    @Path("re/{id}")
    @RolesAllowed(value = {"BIDDER"})
    public Response rebid(@PathParam("id") Long id, @QueryParam("bidAmount") BigDecimal bidAmount) {
        if (bidAmount == null || bidAmount.compareTo(BigDecimal.ZERO) < 1) {
            return Response.status(Status.BAD_REQUEST)
                    .entity(new APIResponse(Status.BAD_REQUEST.getStatusCode(), "bid amount is missing or is <= 0"))
                    .build();
        }
        var success = bidService.rebid(id, bidAmount);
        if (success) {
            return Response.ok(new APIResponse(200, "success")).build();
        } else {
            return Response.status(422).entity(new APIResponse(422, "bid not found")).build();
        }
    }
}
