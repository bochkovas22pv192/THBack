package com.example.THBack.services;

import com.example.THBack.dto.OfferGetDTO;
import com.example.THBack.dto.OfferPostAndPutDTO;
import com.example.THBack.exceptions.EmployeeNotFoundException;
import com.example.THBack.mappers.OfferMapper;
import com.example.THBack.models.Employee;
import com.example.THBack.models.Offer;
import com.example.THBack.models.enums.OfferState;
import com.example.THBack.repository.EmployeeRepository;
import com.example.THBack.repository.OfferPhotoRepository;
import com.example.THBack.repository.OfferRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OfferService {

    private final OfferRepository offerRepository;
    private final OfferPhotoRepository offerPhotoRepository;
    private final OfferMapper offerMapper;
    private final EmployeeRepository employeeRepository;

    public OfferGetDTO getOfferById(Long id){
        return offerMapper.ToOfferGetDTO(offerRepository.findById(id).orElse(new Offer()));
    }

    public List<OfferGetDTO> getOfferAll(String type){
        List<Offer> temp;
        if (!EnumUtils.isValidEnum(OfferState.class, type)){
            temp = offerRepository.findAll();
        }
        else{
            temp = offerRepository.findByState(OfferState.valueOf(type));
        }
        List<OfferGetDTO> result = new java.util.ArrayList<>(List.of());
        for(Offer offer : temp){
            result.add(offerMapper.ToOfferGetDTO(offer));
        }
        return result;
    }

    public List<OfferGetDTO> getOfferAllByUser(Long id, String type){
        List<Offer> temp;
        if (!EnumUtils.isValidEnum(OfferState.class, type)){
            temp = offerRepository.findAllByAuthor(employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id)));
        }
        else{
            temp = offerRepository.findByStateAndAuthor(OfferState.valueOf(type), employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id)));
        }
        List<OfferGetDTO> result = new java.util.ArrayList<>(List.of());
        for(Offer offer : temp){
            result.add(offerMapper.ToOfferGetDTO(offer));
        }
        return result;
    }

    public OfferPostAndPutDTO createOffer(OfferPostAndPutDTO offer){
        return offerMapper.toOfferPostAndPutDTO(offerRepository.save(offerMapper.FromOfferPostAndPutDTO(offer)));
    }

    public  OfferPostAndPutDTO updateOffer(Long id, OfferPostAndPutDTO offer){
        Offer newOffer = offerMapper.FromOfferPostAndPutDTO(offer);
        return offerRepository.findById(id).map(oldOffer -> {

            oldOffer.setTitle(newOffer.getTitle());
            oldOffer.setDescription(newOffer.getDescription());
            oldOffer.setOfferPhoto(newOffer.getOfferPhoto());
            return offerMapper.toOfferPostAndPutDTO(offerRepository.save(oldOffer));
        }).orElseThrow(() -> new EmployeeNotFoundException(id));
    }
}
