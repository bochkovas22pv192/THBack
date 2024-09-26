package com.example.THBack.controllers;

import com.example.THBack.dto.OfferGetDTO;
import com.example.THBack.dto.OfferPostAndPutDTO;
import com.example.THBack.dto.OfferRatePutDTO;
import com.example.THBack.models.OfferRate;
import com.example.THBack.services.OfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/offer")
public class OfferController {
    private final OfferService service;

    static class GetOfferAllType {
        public String type;
    }

    static class OfferStateUpdateData {
        public String state;
        public Long adminId;
    }

    static class OfferScoreUpdateData {
        public String scoreType;
        public Long employeeId;
    }


    @GetMapping("/{id}")
    OfferGetDTO offerById(@PathVariable Long id) {return service.getOfferById(id);}

    @GetMapping("/")
    List<OfferGetDTO> offerAll(@RequestBody GetOfferAllType getOfferAllType) {
        return service.getOfferAll(getOfferAllType.type);
    }
    @GetMapping("user/{id}")
    List<OfferGetDTO> offerAll(@PathVariable Long id, @RequestBody GetOfferAllType getOfferAllType) {
        return service.getOfferAllByUser(id, getOfferAllType.type);
    }

    @PostMapping("/")
    OfferPostAndPutDTO offerCreate(@RequestBody OfferPostAndPutDTO offer){
        return service.createOffer(offer);
    }

    @PutMapping("/{id}")
    OfferPostAndPutDTO offerUpdate(@PathVariable Long id, @RequestBody OfferPostAndPutDTO offer){
        return service.updateOffer(id, offer);
    }
    @DeleteMapping("/{id}")
    void offerDelete(@PathVariable Long id){
        service.deleteOffer(id);
    }

    @PutMapping("/status/{id}")
    OfferPostAndPutDTO offerStatusUpdate(@PathVariable Long id, @RequestBody OfferStateUpdateData offerStateUpdateData){
        return service.updateOfferStatus(id, offerStateUpdateData.state, offerStateUpdateData.adminId);
    }

    @PutMapping("/score/{id}")
    OfferRatePutDTO offerScoreUpdate(@PathVariable Long id, @RequestBody OfferScoreUpdateData offerScoreUpdateData){
        return service.updateOfferScore(id, offerScoreUpdateData.scoreType, offerScoreUpdateData.employeeId);
    }


}
