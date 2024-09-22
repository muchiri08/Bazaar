package muchiri.app.bazaar;

import io.quarkus.logging.Log;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import muchiri.app.bazaar.user.TokenExpiredException;
import muchiri.app.bazaar.user.TokenNotFoudException;
import muchiri.app.bazaar.user.UserException;

@Provider
public class APIExceptionHandler implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable exception) {
        if (exception instanceof UserException e) {
            return Response.status(422).entity(new APIResponse(422, e.getMessage())).build();
        }
        if (exception instanceof TokenExpiredException e) {
            return Response.status(200).entity(new APIResponse(200, e.getMessage())).build();
        }
        if (exception instanceof TokenNotFoudException e) {
            return Response.status(404).entity(new APIResponse(404, e.getMessage())).build();
        }
        Log.error("UNAHANDLED ERROR:", exception);
        return Response.serverError().entity(new APIResponse(500, "internal server error")).build();
    }

}
