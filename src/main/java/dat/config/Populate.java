package dat.config;


import dat.entities.RacketDetails;
import dat.entities.TennisRacket;
import jakarta.persistence.EntityManagerFactory;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class Populate {
    public static void main(String[] args) {

        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

          Set<RacketDetails> pureaero = getPureaero();
          Set<RacketDetails> prostaff = getWilson();

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

    @NotNull
    public static Set<RacketDetails> getPureaero(){
    RacketDetails aero1 = new RacketDetails(300,"16X19","3", 1700, RacketDetails.racketSize.SENIOR);
    RacketDetails aero2 = new RacketDetails(300,"16X18","1", 1700, RacketDetails.racketSize.JUNIOR);
    RacketDetails aero3 = new RacketDetails(280,"16X19","2", 1700, RacketDetails.racketSize.JUNIOR);
    RacketDetails aero4 = new RacketDetails(320,"16X19","2", 1700, RacketDetails.racketSize.SENIOR);
    RacketDetails aero5 = new RacketDetails(317,"16X19","4", 1700, RacketDetails.racketSize.SENIOR);
    RacketDetails[] racketarray = {aero1, aero2, aero3, aero4, aero5};
    return Set.of(racketarray);
}
    @NotNull
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
