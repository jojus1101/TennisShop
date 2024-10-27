package dat.entities;
import dat.dtos.TennisRacketDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "TennisRacket")
public class TennisRacket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tennis_racketID", nullable = false)
    private Integer id;

    @Setter
    @Column(name = "brand", nullable = false)
    private String brand;

    @Setter
    @Column(name = "model", nullable = false)
    private String model;


    @OneToMany(mappedBy = "tennisracket", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<RacketDetails> racketdetails = new HashSet<>();


public TennisRacket(String brand, String model) {
    this.brand = brand;
    this.model = model;
}

public TennisRacket(TennisRacketDTO tennisRacketDTO) {
    this.id = tennisRacketDTO.getId();
    this.brand = tennisRacketDTO.getBrand();
    this.model = tennisRacketDTO.getModel();

}

public void setRacketdetails(Set<RacketDetails> racketdetails) {
    if(racketdetails != null) {
        this.racketdetails = racketdetails;
        for (RacketDetails racketDetails : racketdetails) {
            racketDetails.setTennisracket(this);
        }
    }
}
public void addRacketDetails(RacketDetails racketDetails) {
    if(racketdetails != null) {
        this.racketdetails.add(racketDetails);
        racketDetails.setTennisracket(this);
    }
}
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TennisRacket tennisRacket = (TennisRacket) o;
        return Objects.equals(brand, tennisRacket.brand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(brand);
    }


}
