package muchiri.app.bazaar;

import io.quarkus.logging.Log;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import muchiri.app.bazaar.user.UserException;

@Provider
public class APIExceptionHandler implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable exception) {
        if (exception instanceof UserException e) {
            return Response.status(422).entity(new APIResponse(422, e.getMessage())).build();
        }
        Log.error("UNAHANDLED ERROR:", exception);
        return Response.serverError().entity(new APIResponse(500, "internal server error")).build();
    }

}
