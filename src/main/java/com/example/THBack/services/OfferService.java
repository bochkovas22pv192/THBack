package com.example.THBack.services;

import com.example.THBack.dto.OfferGetDTO;
import com.example.THBack.mappers.OfferMapper;
import com.example.THBack.repository.OfferPhotoRepository;
import com.example.THBack.repository.OfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OfferService {

    private final OfferRepository offerRepository;
    private final OfferPhotoRepository offerPhotoRepository;
    private final OfferMapper offerMapper;

    public OfferGetDTO getOfferById(Long id){
        return offerMapper.ToOfferGetDTO(offerRepository.findById(id).get());
    }
}
