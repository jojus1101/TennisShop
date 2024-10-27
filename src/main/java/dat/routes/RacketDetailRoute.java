package dat.routes;

import dat.controllers.impl.RacketDetailController;
import dat.security.enums.Role;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;
import static io.javalin.apibuilder.ApiBuilder.delete;

public class RacketDetailRoute {

    private final RacketDetailController racketDetailController = new RacketDetailController();

    protected EndpointGroup getRoutes() {

        return () -> {
//
            post("/racket/{id}", racketDetailController::create, Role.ADMIN);
            get("/", racketDetailController::readAll);
            get("/{id}", racketDetailController::read);
            put("/{id}", racketDetailController::update, Role.ADMIN);
            delete("/{id}", racketDetailController::delete, Role.ADMIN);
        };
    }
}
