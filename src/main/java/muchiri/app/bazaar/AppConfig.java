package muchiri.app.bazaar;

import org.jdbi.v3.core.Jdbi;

import io.agroal.api.AgroalDataSource;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.ext.Provider;

@Provider
public class AppConfig {
    @Inject
    private AgroalDataSource ds;

    @Singleton
    @Produces
    public Jdbi jdbi() {
        return Jdbi.create(ds);
    }
}
