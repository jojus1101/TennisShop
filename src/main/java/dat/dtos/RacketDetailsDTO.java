package dat.dtos;

import dat.entities.RacketDetails;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@NoArgsConstructor
@Getter
public class RacketDetailsDTO {
    private Integer Id;
    private double weight;
    private String stringPattern;
    private String gripSize;
    private double price;
    private RacketDetails.racketSize size;

    public RacketDetailsDTO(RacketDetails racketDetails) {
        this.Id = racketDetails.getRacketDetailID();
        this.weight = racketDetails.getWeight();
        this.stringPattern = racketDetails.getStringPattern();
        this.gripSize = racketDetails.getGripSize();
        this.price = racketDetails.getPrice();
        this.size = racketDetails.getSize();
    }

//    public static List<RacketDetailsDTO>toRacketDetailsDTO(List<RacketDetails> racketDetailsList) {
//        return List.of(racketDetailsList.stream().map(RoomDTO::new).toArray(RoomDTO[]::new));
//    }
}
