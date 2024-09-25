package muchiri.app.bazaar;

import io.quarkus.logging.Log;
import jakarta.ws.rs.NotAllowedException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import muchiri.app.bazaar.product.ProductException;
import muchiri.app.bazaar.user.TokenExpiredException;
import muchiri.app.bazaar.user.TokenNotFoudException;
import muchiri.app.bazaar.user.UserException;

@Provider
public class APIExceptionHandler implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable exception) {
        return switch (exception) {
            case UserException e -> Response.status(422).entity(new APIResponse(422, e.getMessage())).build();
            case TokenExpiredException e -> Response.status(200).entity(new APIResponse(200, e.getMessage())).build();
            case TokenNotFoudException e -> Response.status(404).entity(new APIResponse(404, e.getMessage())).build();
            case ProductException e -> Response.status(422).entity(new APIResponse(422, e.getMessage())).build();
            case NotFoundException e -> Response.status(Status.NOT_FOUND).build();
            case NotAllowedException e -> Response.status(Status.METHOD_NOT_ALLOWED).build();
            default -> {
                Log.error("UNHANDLED ERROR:", exception);
                yield Response.status(500).entity(new APIResponse(500, "Internal Server Error")).build();
            }
        };
    }

}
