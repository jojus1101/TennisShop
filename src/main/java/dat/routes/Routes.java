package dat.routes;

import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Routes {

    private final RacketRoute racketRoute = new RacketRoute();
    private final RacketDetailRoute racketDetailRoute = new RacketDetailRoute();

    public EndpointGroup getRoutes() {
        return () -> {
                path("/rackets",racketRoute.getRoutes());
                path("/racketdetails",racketDetailRoute.getRoutes());
        };
    }
}
