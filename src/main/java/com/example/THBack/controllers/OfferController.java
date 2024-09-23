package com.example.THBack.controllers;

import com.example.THBack.dto.OfferGetDTO;
import com.example.THBack.services.OfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/offer")
public class OfferController {
    private final OfferService service;

    @GetMapping("/{id}")
    OfferGetDTO offerById(@PathVariable Long id) {return service.getOfferById(id);}
}
