package dat.daos.impl;

import dat.daos.IDAO;
import dat.dtos.TennisRacketDTO;
import dat.entities.RacketDetails;
import dat.entities.TennisRacket;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)

public class TennisRacketDAO implements IDAO<TennisRacketDTO, Integer> {

    private static TennisRacketDAO instance;
    private static EntityManagerFactory emf;

    Set<RacketDetails> pureaero = getPureaero();
    Set<RacketDetails> prostaff = getWilson();

    public static TennisRacketDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new TennisRacketDAO();
        }
        return instance;
    }
    @Override
    public TennisRacketDTO read(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            TennisRacket racket = em.find(TennisRacket.class, id);
            return racket != null ? new TennisRacketDTO(racket) : null; // Create a DTO from the entity
        }
    }
    @Override
    public List<TennisRacketDTO> readAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<TennisRacketDTO> query = em.createQuery("SELECT new dat.dtos.TennisRacketDTO(t) FROM TennisRacket t", TennisRacketDTO.class);
            return query.getResultList();
        }

    }

    @Override
    public TennisRacketDTO create(TennisRacketDTO tennisRacketDTO) {
        try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            TennisRacket racket = new TennisRacket(tennisRacketDTO);
            em.persist(racket);
            em.getTransaction().commit();
            return new TennisRacketDTO(racket);
        }
    }


@Override
public TennisRacketDTO update(Integer integer, TennisRacketDTO tennisRacketDTO) {
    try (EntityManager em = emf.createEntityManager()) {
        em.getTransaction().begin();

        // Find the existing TennisRacket by ID
        TennisRacket racket = em.find(TennisRacket.class, integer);
        if (racket == null) {
            // Handle case where the racket is not found
            return null; // or throw an exception
        }

        // Update the racket's fields with the new values from the DTO
        racket.setBrand(tennisRacketDTO.getBrand());
        racket.setModel(tennisRacketDTO.getModel());

        // If you have a method to update the associated RacketDetails, you may call it here

        // Merge the updated entity back to the database
        TennisRacket mergedRacket = em.merge(racket);
        em.getTransaction().commit();

        // Return a new DTO based on the updated entity
        return new TennisRacketDTO(mergedRacket);
    }
}

@Override
public void delete(Integer integer) {
    try (EntityManager em = emf.createEntityManager()) {
        em.getTransaction().begin();
        TennisRacket tennisRacket = em.find(TennisRacket.class, integer);
        if (tennisRacket != null) {
            em.remove(tennisRacket);
        }
        em.getTransaction().commit();
    }
}

@Override
public boolean validatePrimaryKey(Integer integer) {
    try (EntityManager em = emf.createEntityManager()) {
        TennisRacket racket = em.find(TennisRacket.class, integer);
        return racket != null;
    }
}
    public void populate(){
    try(var em = emf.createEntityManager()) {
        em
                .getTransaction()
                .begin();
        TennisRacket tennisRacket = new TennisRacket("Babolat", "Pure Aero");
        TennisRacket tennisRacket2 = new TennisRacket("Wilson", "Pro staff");
        tennisRacket.setRacketdetails(pureaero);
        tennisRacket2.setRacketdetails(prostaff);
        em.persist(tennisRacket);
        em.persist(tennisRacket2);
        em.getTransaction().commit();

    }
    }
    public static Set<RacketDetails> getPureaero(){
        RacketDetails aero1 = new RacketDetails(300,"16X19","3", 1700, RacketDetails.racketSize.SENIOR);
        RacketDetails aero2 = new RacketDetails(300,"16X18","1", 1700, RacketDetails.racketSize.JUNIOR);
        RacketDetails aero3 = new RacketDetails(280,"16X19","2", 1700, RacketDetails.racketSize.JUNIOR);
        RacketDetails aero4 = new RacketDetails(320,"16X19","2", 1700, RacketDetails.racketSize.SENIOR);
        RacketDetails aero5 = new RacketDetails(317,"16X19","4", 1700, RacketDetails.racketSize.SENIOR);
        RacketDetails[] racketarray = {aero1, aero2, aero3, aero4, aero5};
        return Set.of(racketarray);
    }

    public static Set<RacketDetails> getWilson(){
        RacketDetails prostaf1 = new RacketDetails(320,"18X20","2", 2000, RacketDetails.racketSize.SENIOR);
        RacketDetails prostaf2 = new RacketDetails(320,"16X19","3", 2000, RacketDetails.racketSize.SENIOR);
        RacketDetails prostaf3 = new RacketDetails(300,"18X20","1", 1700, RacketDetails.racketSize.JUNIOR);
        RacketDetails prostaf4 = new RacketDetails(320,"16X19","3", 2000, RacketDetails.racketSize.SENIOR);
        RacketDetails prostaf5 = new RacketDetails(320,"16X18","4", 2000, RacketDetails.racketSize.SENIOR);
        RacketDetails[] wilsons = {prostaf1, prostaf2, prostaf3, prostaf4, prostaf5};
        return Set.of(wilsons);

    }

}
