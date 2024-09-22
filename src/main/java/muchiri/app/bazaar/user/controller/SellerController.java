package muchiri.app.bazaar.user.controller;

import java.util.concurrent.ExecutorService;

import io.quarkus.virtual.threads.VirtualThreads;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import muchiri.app.bazaar.APIResponse;
import muchiri.app.bazaar.mailer.Mailer;
import muchiri.app.bazaar.user.model.Seller;
import muchiri.app.bazaar.user.service.TokenService;
import muchiri.app.bazaar.user.service.UserService;

@Path("sellers")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
public class SellerController {
    @Inject
    UserService userService;
    @Inject
    Mailer mailer;
    @Inject
    @VirtualThreads
    ExecutorService executorService;
    @Inject
    TokenService tokenService;

    @POST
    @Path("new")
    public Response newSeller(@Valid Seller seller) {
        var newSeller = userService.newSeller(seller);
        var status = Status.ACCEPTED.getStatusCode();
        var response = new APIResponse(status, "success");

        var token = tokenService.generateToken(newSeller.getId());
        tokenService.insert(token);

        executorService.submit(() -> {
            mailer.sendEmail(newSeller.getName(), newSeller.getEmail(), token.getPlain());

        });

        return Response.status(status).entity(response).build();
    }
}
