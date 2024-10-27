package dat.daos.impl;

import dat.daos.IDAO;
import dat.dtos.RacketDetailsDTO;
import dat.dtos.TennisRacketDTO;
import dat.entities.RacketDetails;
import dat.entities.TennisRacket;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class RacketDetailsDAO implements IDAO<RacketDetailsDTO, Integer> {
    private static RacketDetailsDAO instance;
    private static EntityManagerFactory emf;

    public static RacketDetailsDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new RacketDetailsDAO();
        }
        return instance;
    }

    public TennisRacketDTO addDetails(Integer tennisId, RacketDetailsDTO racketDetailsDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            // Create a new RacketDetails from the DTO
            RacketDetails racketDetails = new RacketDetails(racketDetailsDTO);

            // Find the TennisRacket by ID
            TennisRacket tennisRacket = em.find(TennisRacket.class, tennisId);

            // Add the RacketDetails to the TennisRacket
            tennisRacket.addRacketDetails(racketDetails);

            // Persist the new RacketDetails
            em.persist(racketDetails);

            // Merge the TennisRacket (this updates it in the context)
            TennisRacket mergedRacket = em.merge(tennisRacket);

            // Commit the transaction
            em.getTransaction().commit();

            // Return the DTO representation of the merged TennisRacket
            return new TennisRacketDTO(mergedRacket);
        }
    }

    @Override
    public RacketDetailsDTO read(Integer integer) {
        try(EntityManager em = emf.createEntityManager()) {
            RacketDetails racketDetails = em.find(RacketDetails.class, integer);
            return racketDetails != null ? new RacketDetailsDTO(racketDetails) : null;
        }
    }

    @Override
    public List<RacketDetailsDTO> readAll() {
        try(EntityManager em = emf.createEntityManager()) {
            TypedQuery<RacketDetailsDTO> query = em.createQuery("SELECT new dat.dtos.RacketDetailsDTO(r) FROM RacketDetails r", RacketDetailsDTO.class);
            return query.getResultList();
        }
    }

    @Override
    public RacketDetailsDTO create(RacketDetailsDTO racketDetailsDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            RacketDetails racketDetails = new RacketDetails(racketDetailsDTO);
            em.persist(racketDetails);
            em.getTransaction().commit();
            return new RacketDetailsDTO(racketDetails);
        }
    }

    @Override
    public RacketDetailsDTO update(Integer integer, RacketDetailsDTO racketDetailsDTO) {
        try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            RacketDetails racketDetails = em.find(RacketDetails.class, integer);
            racketDetails.setWeight(racketDetailsDTO.getWeight());
            racketDetails.setStringPattern(racketDetailsDTO.getStringPattern());
            racketDetails.setPrice(racketDetailsDTO.getPrice());
            racketDetails.setGripSize(racketDetailsDTO.getGripSize());
            racketDetails.setSize(racketDetailsDTO.getSize());
            RacketDetails mergedRacket = em.merge(racketDetails);
            em.getTransaction().commit();
            return new RacketDetailsDTO(mergedRacket);
        }
    }

    @Override
    public void delete(Integer integer) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            RacketDetails racketDetails = em.find(RacketDetails.class, integer);
            if (racketDetails != null){
                em.remove(racketDetails);
            }
            em.getTransaction().commit();
        }
    }

    @Override
    public boolean validatePrimaryKey(Integer integer) {
        try (EntityManager em = emf.createEntityManager()) {
            RacketDetails racketDetails = em.find(RacketDetails.class, integer);
            return racketDetails != null;
        }
    }

}
