package dat.dtos;

import dat.entities.RacketDetails;
import dat.entities.TennisRacket;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
@Getter
@Setter
@NoArgsConstructor
public class TennisRacketDTO {
    private Integer id;
    private String brand;
    private String model;
    private Set<RacketDetailsDTO> racketdetails = new HashSet<>();


    public TennisRacketDTO(TennisRacket racket) {
        this.id = racket.getId();
        this.brand = racket.getBrand();
        this.model = racket.getModel();
        if (racket.getRacketdetails() != null) {
            racket.getRacketdetails().forEach(racketDetail ->
                    this.racketdetails.add(new RacketDetailsDTO(racketDetail)));
        }
    }
    public TennisRacketDTO (String brand, String model) {
        this.brand = brand;
        this.model = model;
    }
    public static List<RacketDetailsDTO> toRacketDetailsDTO(List<RacketDetails> racketdetails) {
        return racketdetails.stream().map(RacketDetailsDTO::new).collect(Collectors.toList());
    }

}
