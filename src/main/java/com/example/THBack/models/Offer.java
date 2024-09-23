package com.example.THBack.models;

import com.example.THBack.models.enums.OfferState;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name ="offer")
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name="title", nullable = false)
    private String title;

    @Column(name="description", nullable = false)
    private String description;

    @Column(name="state", nullable = false)
    private OfferState state;

    @Column(name="date_published", nullable = false)
    private LocalDate datePublished;

    @ManyToOne
    @JoinColumn(name="author_id")
    private Employee author;

    @OneToMany(mappedBy = "offer")
    @ToString.Exclude
    private Set<OfferRate> offerRate;

    @OneToMany(mappedBy = "offer")
    @ToString.Exclude
    private Collection<OfferPhoto> offerPhoto;


}
