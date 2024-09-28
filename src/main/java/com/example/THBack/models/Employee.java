package com.example.THBack.models;

import java.time.LocalDate;
import java.util.Set;


import jakarta.persistence.*;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@ToString
@Entity
@Table
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column
    private String fatherName;

    @Column(nullable = false)
    private String rank;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private LocalDate birthday;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String telegram;

    @Column(nullable = false)
    private LocalDate workBeginningDate;

    @Lob
    @Column
    private byte[] photo;

    @OneToMany(mappedBy = "author")
    @ToString.Exclude
    private Set<Offer> offer;

    @OneToMany(mappedBy = "author")
    @ToString.Exclude
    private Set<OfferRate> offerRate;

    public Employee(String firstName, String lastName, String fatherName, String rank, String phoneNumber, LocalDate birthday,
                    String email, String telegram, LocalDate workBeginningDate, byte[] photo) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.fatherName = fatherName;
        this.rank = rank;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
        this.email = email;
        this.telegram = telegram;
        this.workBeginningDate = workBeginningDate;
        this.photo = photo;
    }
}
