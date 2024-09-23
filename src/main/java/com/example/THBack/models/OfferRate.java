package com.example.THBack.models;

import com.example.THBack.models.enums.OfferRateType;
import jakarta.persistence.*;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@ToString
@Entity
@Table(name ="offer_rate")
public class OfferRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name="type", nullable = false)
    private OfferRateType type;

    @ManyToOne
    @JoinColumn(name="offer_id")
    private Offer offer;

    @ManyToOne
    @JoinColumn(name="author_id")
    private Employee author;
}
