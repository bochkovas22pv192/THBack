package com.example.THBack.models;

import com.example.THBack.models.enums.OfferState;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Collection;



@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private OfferState state;

    @Column(nullable = false)
    private LocalDate datePublished;

    @ManyToOne
    @JoinColumn(name="author_id")
    private Employee author;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "offer")
    @ToString.Exclude
    private Collection<OfferRate> offerRate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "offer")
    @ToString.Exclude
    private Collection<OfferPhoto> offerPhoto;


    public Offer(String title, String description, OfferState state, LocalDate datePublished, Employee author, Collection<OfferRate> offerRate, Collection<OfferPhoto> offerPhoto) {
        this.title = title;
        this.description = description;
        this.state = state;
        this.datePublished = datePublished;
        this.author = author;
        this.offerRate = offerRate;
        this.offerPhoto = offerPhoto;
    }
}
