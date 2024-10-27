package dat.entities;

import dat.dtos.RacketDetailsDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
@Table(name="racket_detials")
public class RacketDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "racket_detail_id", nullable = false, unique = true)
    private Integer racketDetailID;

    @Setter
    @Column(name = "weight",nullable = false)
    private double weight; // in grams


    @Setter
    @Column(name = "string_pattern",nullable = false)
    private String stringPattern; // e.g. "16x19

    @Setter
    @Column(name = "grip_size",nullable = false)
    private String gripSize;

    @Setter
    @Column(name = "price",nullable = false)
    private double price;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "size", nullable = false)
    private RacketDetails.racketSize size;

    @Setter
    @ManyToOne
    @JoinColumn(name = "tennis_racket_id", nullable = false)
    private TennisRacket tennisracket;

    public RacketDetails(double weight, String stringPattern, String gripSize, double price, RacketDetails.racketSize size) {
        this.weight = weight;
        this.stringPattern = stringPattern;
        this.gripSize = gripSize;
        this.price = price;
        this.size = size;
    }
    public RacketDetails(RacketDetailsDTO racketDetailsDTO){
        this.racketDetailID = racketDetailsDTO.getId();
        this.weight = racketDetailsDTO.getWeight();
        this.stringPattern = racketDetailsDTO.getStringPattern();
        this.gripSize = racketDetailsDTO.getGripSize();
        this.price = racketDetailsDTO.getPrice();
        this.size = racketDetailsDTO.getSize();
    }

    public enum racketSize{
        SENIOR, JUNIOR
    }
}
