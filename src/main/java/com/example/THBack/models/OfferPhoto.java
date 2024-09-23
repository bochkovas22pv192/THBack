package com.example.THBack.models;

import jakarta.persistence.*;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@ToString
@Entity
@Table(name ="offer_photo")
public class OfferPhoto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "photo")
    private byte[] photo;

    @ManyToOne
    @JoinColumn(name="offer_id")
    private Offer offer;
}