package com.example.THBack.services;

import com.example.THBack.dto.OfferGetDTO;
import com.example.THBack.dto.OfferPostAndPutDTO;
import com.example.THBack.dto.OfferRatePutDTO;
import com.example.THBack.exceptions.*;
import com.example.THBack.mappers.OfferMapper;
import com.example.THBack.models.Employee;
import com.example.THBack.models.Offer;
import com.example.THBack.models.OfferPhoto;
import com.example.THBack.models.OfferRate;
import com.example.THBack.models.enums.OfferRateType;
import com.example.THBack.models.enums.OfferState;
import com.example.THBack.repository.EmployeeRepository;
import com.example.THBack.repository.OfferPhotoRepository;
import com.example.THBack.repository.OfferRateRepository;
import com.example.THBack.repository.OfferRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.stereotype.Service;


import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class OfferService {

    private final OfferRepository offerRepository;
    private final OfferPhotoRepository offerPhotoRepository;
    private final OfferRateRepository offerRateRepository;
    private final OfferMapper offerMapper;
    private final EmployeeRepository employeeRepository;

    private void checkForOfferExceptions(Offer offer) {
        if (offer.getTitle().isEmpty()) {
            throw new TitleEmptyException();
        }
        if (offer.getDescription().isEmpty()) {
            throw new DescriptionEmptyException();
        }
        if (offer.getOfferPhoto().size() > 10) {
            throw new ImageCountException();
        }

        for (OfferPhoto photo : offer.getOfferPhoto()) {
            if (photo.getPhoto().length > 5000000) {
                throw new ImageSizeException();
            }
        }
    }

    public OfferGetDTO getOfferById(Long id) {
        return offerMapper.offerToOfferGetDTO(offerRepository.findById(id).orElse(new Offer()));
    }

    public List<OfferGetDTO> getOfferAll(String state) {
        List<Offer> OfferListFromRepository;
        if (!EnumUtils.isValidEnum(OfferState.class, state)) {
            OfferListFromRepository = offerRepository.findAll();
        } else {
            OfferListFromRepository = offerRepository.findByState(OfferState.valueOf(state));
        }
        List<OfferGetDTO> result = new java.util.ArrayList<>(List.of());
        for (Offer offer : OfferListFromRepository) {
            result.add(offerMapper.offerToOfferGetDTO(offer));
        }
        return result;
    }

    public List<OfferGetDTO> getOfferAllByUser(Long id, String state) {
        List<Offer> temp;
        if (!EnumUtils.isValidEnum(OfferState.class, state)) {
            temp = offerRepository.findAllByAuthor(employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id)));
        } else {
            temp = offerRepository.findByStateAndAuthor(OfferState.valueOf(state), employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id)));
        }
        List<OfferGetDTO> result = new java.util.ArrayList<>(List.of());
        for (Offer offer : temp) {
            result.add(offerMapper.offerToOfferGetDTO(offer));
        }
        return result;
    }

    public OfferPostAndPutDTO createOffer(OfferPostAndPutDTO offer) {
        Offer newOffer = offerMapper.offerFromOfferPostAndPutDTO(offer);
        checkForOfferExceptions(newOffer);
        Offer newOfferReturn = offerRepository.save(offerMapper.offerFromOfferPostAndPutDTO(offer));
        for (OfferPhoto photo : newOfferReturn.getOfferPhoto()) {
            offerPhotoRepository.save(photo);
        }
        return offerMapper.offerToOfferPostAndPutDTO(newOffer);
    }

    public OfferPostAndPutDTO updateOffer(Long id, OfferPostAndPutDTO offer) {
        Offer newOffer = offerMapper.offerFromOfferPostAndPutDTO(offer);
        checkForOfferExceptions(newOffer);
        Offer oldOffer = offerRepository.findById(id).orElseThrow(() -> new OfferNotFoundException(id));
        for (OfferPhoto photo : oldOffer.getOfferPhoto()) {
            offerPhotoRepository.deleteById(photo.getId());
        }
        newOffer.setId(id);
        for (OfferPhoto photo : newOffer.getOfferPhoto()) {
            offerPhotoRepository.save(photo);
        }

        return offerMapper.offerToOfferPostAndPutDTO(newOffer);
    }

    public void deleteOffer(Long id) {
        offerRepository.deleteById(id);
    }

    public OfferPostAndPutDTO updateOfferStatus(Long id, String state, Long adminId) {
        Offer newOffer = offerRepository.findById(id).orElseThrow(() -> new OfferNotFoundException(id));
        if (adminId.equals(newOffer.getAuthor().getId())) {
            throw new ApprovingYouOwnOfferException();
        }
        newOffer.setState(OfferState.valueOf(state));
        return offerMapper.offerToOfferPostAndPutDTO(offerRepository.save(newOffer));
    }

    public OfferRatePutDTO updateOfferScore(Long id, String scoreType, Long employeeId) {
        Employee author = employeeRepository.findById(employeeId).orElseThrow(() -> new EmployeeNotFoundException(id));
        Offer offer = offerRepository.findById(id).orElseThrow(() -> new OfferNotFoundException(id));
        OfferRate offerRate = offerRateRepository.findByOfferAndAuthor(offer, author);

        if (offerRate == null) {
            OfferRate newOfferRate = new OfferRate();
            newOfferRate.setOffer(offer);
            newOfferRate.setAuthor(author);
            newOfferRate.setType(OfferRateType.valueOf(scoreType));

            return offerMapper.offerRateToOfferRatePutDTO(offerRateRepository.save(newOfferRate));
        }

        offerRate.setType(OfferRateType.valueOf(scoreType));
        return offerMapper.offerRateToOfferRatePutDTO(offerRateRepository.save(offerRate));
    }
}
