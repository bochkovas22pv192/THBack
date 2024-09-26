package com.example.THBack.repository;


import com.example.THBack.models.Employee;
import com.example.THBack.models.Offer;
import com.example.THBack.models.OfferRate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferRateRepository extends JpaRepository<OfferRate, Long> {
    OfferRate findByOfferAndAuthor(Offer offer, Employee author);
}
