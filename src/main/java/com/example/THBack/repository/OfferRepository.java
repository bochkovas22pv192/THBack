package com.example.THBack.repository;

import com.example.THBack.models.Employee;
import com.example.THBack.models.Offer;
import com.example.THBack.models.enums.OfferState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {
    List<Offer> findByState(OfferState state);
    List<Offer> findByStateAndAuthor(OfferState state, Employee author);
    List<Offer> findAllByAuthor(Employee author);
}
