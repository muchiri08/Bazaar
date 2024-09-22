package muchiri.app.bazaar.user.controller;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import muchiri.app.bazaar.APIResponse;
import muchiri.app.bazaar.user.service.TokenService;
import muchiri.app.bazaar.user.service.UserService;

@Path("")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
public class AuthController {
    @Inject
    TokenService tokenService;
    @Inject
    UserService userService;

    @POST
    @Path("account/activate")
    public Response activate(@QueryParam(value = "token") String token) {
        if (token.isBlank()) {
            throw new WebApplicationException(
                    Response.status(Status.BAD_REQUEST)
                            .entity(new APIResponse(400, "token is required"))
                            .build());
        }
        long userId = tokenService.verifyToken(token);
        System.out.println("activating user with ID: %d".formatted(userId));
        userService.activateUser(userId);
        return Response.ok(new APIResponse(200, "success")).build();
    }
}
