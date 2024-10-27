package dat.routes;

import dat.controllers.impl.TennisRacketController;
import dat.security.enums.Role;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;
import static io.javalin.apibuilder.ApiBuilder.delete;

public class RacketRoute {

    private final TennisRacketController racketController = new TennisRacketController();

    protected EndpointGroup getRoutes() {

        return () -> {
            get("/populate", racketController::populate);
            post("/", racketController::create, Role.ADMIN);
            get("/", racketController::readAll);
            get("/{id}", racketController::read);
            put("/{id}", racketController::update, Role.ADMIN);
            delete("/{id}", racketController::delete, Role.ADMIN);
        };
    }
}
