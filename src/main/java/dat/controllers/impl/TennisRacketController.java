package dat.controllers.impl;

import dat.config.HibernateConfig;
import dat.controllers.IController;
import dat.daos.impl.TennisRacketDAO;
import dat.dtos.TennisRacketDTO;
import dat.entities.TennisRacket;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class TennisRacketController implements IController<TennisRacketDTO, Integer> {
    private final TennisRacketDAO dao;

    public TennisRacketController() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        this.dao = TennisRacketDAO.getInstance(emf);
    }

    @Override
    public void read(Context ctx) {
        // request
        int id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        // DTO
        TennisRacketDTO tennisRacketDTO = dao.read(id);
        // response
        ctx.res().setStatus(200);
        ctx.json(tennisRacketDTO, TennisRacketDTO.class);
    }


    @Override
    public void readAll(Context ctx) {
        // List of DTOS
        List<TennisRacketDTO> tennisRacketDTOS = dao.readAll();
        // response
        ctx.res().setStatus(200);
        ctx.json(tennisRacketDTOS, TennisRacketDTO.class);
    }

    @Override
    public void create(Context ctx) {
        // request
        TennisRacketDTO jsonRequest = ctx.bodyAsClass(TennisRacketDTO.class);
        // DTO
        TennisRacketDTO tennisRacketDTO = dao.create(jsonRequest);
        // response
        ctx.res().setStatus(201);
        ctx.json(tennisRacketDTO, TennisRacketDTO.class);
    }

    @Override
    public void update(Context ctx) {
        // request
        int id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        // dto
        TennisRacketDTO tennisRacketDTO = dao.update(id, validateEntity(ctx));
        // response
        ctx.res().setStatus(200);
        ctx.json(tennisRacketDTO, TennisRacket.class);
    }

    @Override
    public void delete(Context ctx) {
        // request
        int id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        dao.delete(id);
        // response
        ctx.res().setStatus(204);
    }

    @Override
    public boolean validatePrimaryKey(Integer integer) {
        return dao.validatePrimaryKey(integer);
    }

    @Override
    public TennisRacketDTO validateEntity(Context ctx) {
        return ctx.bodyValidator(TennisRacketDTO.class)
                .check(t ->t.getBrand() != null && !t.getBrand().isEmpty(), "Brand must be set")
                .check(t ->t.getModel() != null && !t.getModel().isEmpty(), "Model must be set")
                .get();
    }

    public void populate(Context ctx) {
        dao.populate();
        ctx.res().setStatus(200);
        ctx.json("{ \"message\": \"Database has been populated\" }");
    }
}
