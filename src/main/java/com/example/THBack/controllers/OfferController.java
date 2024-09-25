package com.example.THBack.controllers;

import com.example.THBack.dto.OfferGetDTO;
import com.example.THBack.dto.OfferPostAndPutDTO;
import com.example.THBack.services.OfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/offer")
public class OfferController {
    private final OfferService service;
    static class Type {
        public String type;
    }

    @GetMapping("/{id}")
    OfferGetDTO offerById(@PathVariable Long id) {return service.getOfferById(id);}

    @GetMapping("/")
    List<OfferGetDTO> offerAll(@RequestBody Type type) {
        return service.getOfferAll(type.type);
    }
    @GetMapping("user/{id}")
    List<OfferGetDTO> offerAll(@PathVariable Long id, @RequestBody Type type) {
        return service.getOfferAllByUser(id, type.type);
    }

    @PostMapping("/")
    OfferPostAndPutDTO offerCreate(@RequestBody OfferPostAndPutDTO offer){
        return service.createOffer(offer);
    }

    @PutMapping("/{id}")
    OfferPostAndPutDTO offerUpdate(@PathVariable Long id, @RequestBody OfferPostAndPutDTO offer){
        return service.updateOffer(id, offer);
    }
}
