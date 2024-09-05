package muchiri.app.bazaar;

import org.sql2o.Sql2o;

import io.agroal.api.AgroalDataSource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;

@ApplicationScoped
public class AppConfig {
    @Inject
    private AgroalDataSource ds;

    @Produces
    public Sql2o sql2o() {
        return new Sql2o(ds);
    }
}
