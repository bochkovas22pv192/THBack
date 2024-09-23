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
@Table(name ="employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name="first_name", nullable = false)
    private String firstName;

    @Column(name="last_name", nullable = false)
    private String lastName;

    @Column(name="father_name")
    private String fatherName;

    @Column(name="rank", nullable = false)
    private String rank;

    @Column(name="phone_number", nullable = false)
    private String phoneNumber;

    @Column(name="birthday", nullable = false)
    private LocalDate birthday;

    @Column(name="email", nullable = false)
    private String email;

    @Column(name="telegram", nullable = false)
    private String telegram;

    @Column(name="work_beginning_date", nullable = false)
    private LocalDate workBeginningDate;

    @Lob
    @Column(name="photo")
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
