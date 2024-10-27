package dat.controllers.impl;

import dat.config.HibernateConfig;
import dat.controllers.IController;
import dat.daos.impl.RacketDetailsDAO;
import dat.dtos.RacketDetailsDTO;
import dat.dtos.TennisRacketDTO;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class RacketDetailController implements IController<RacketDetailsDTO, Integer> {
    private final RacketDetailsDAO dao;

    public RacketDetailController() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        this.dao = RacketDetailsDAO.getInstance(emf);
    }

    @Override
    public void read(Context ctx) {
        // request
        int id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        // entity
        RacketDetailsDTO racketDetailsDTO = dao.read(id);
        // response
        ctx.res().setStatus(200);
        ctx.json(racketDetailsDTO, RacketDetailsDTO.class);
    }

    @Override
    public void readAll(Context ctx) {
        // entity
        List<RacketDetailsDTO> racketDetailsDTO = dao.readAll();
        // response
        ctx.res().setStatus(200);
        ctx.json(racketDetailsDTO, RacketDetailsDTO.class);
    }

    @Override
    public void create(Context ctx) {
        RacketDetailsDTO jsonRequest = validateEntity(ctx);
        int racketid = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        TennisRacketDTO tennisRacketDTO = dao.addDetails(racketid, jsonRequest);
        ctx.res().setStatus(200);
        ctx.json(tennisRacketDTO, TennisRacketDTO.class);
    }

    @Override
    public void update(Context ctx) {
        // request
        int id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        // entity
        RacketDetailsDTO racketDetailsDTO = dao.update(id, validateEntity(ctx));
        // response
        ctx.res().setStatus(200);
        ctx.json(racketDetailsDTO, RacketDetailsDTO.class);
    }

    @Override
    public void delete(Context ctx) {
        // request
        int id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        // entity
        dao.delete(id);
        // response
        ctx.res().setStatus(204);

    }

    @Override
    public boolean validatePrimaryKey(Integer integer) {
        return dao.validatePrimaryKey(integer);
    }
    @Override
    public RacketDetailsDTO validateEntity(Context ctx){
        return ctx.bodyValidator(RacketDetailsDTO.class)
                .check(r -> r.getWeight() > 0, "Not valid weight for racket")
                .check(r -> r.getPrice() > 0, "Not valid price for racket")
                .check(r -> r.getStringPattern() != null, "Not valid string pattern for racket")
                .check(r -> r.getGripSize() != null, "Not correct grip size")
                .check(r -> r.getSize() != null, "Not a size")
                .get();
    }
}
