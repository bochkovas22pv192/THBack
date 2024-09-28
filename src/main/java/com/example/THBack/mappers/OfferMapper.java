package com.example.THBack.mappers;


import com.example.THBack.dto.OfferGetDTO;
import com.example.THBack.dto.OfferPostAndPutDTO;
import com.example.THBack.dto.OfferRatePutDTO;
import com.example.THBack.exceptions.EmployeeNotFoundException;
import com.example.THBack.models.Offer;
import com.example.THBack.models.OfferPhoto;
import com.example.THBack.models.OfferRate;
import com.example.THBack.models.enums.OfferRateType;
import com.example.THBack.models.enums.OfferState;
import com.example.THBack.repository.EmployeeRepository;
import com.example.THBack.repository.OfferPhotoRepository;
import com.example.THBack.repository.OfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.LinkedHashSet;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OfferMapper {

    private final OfferRepository offerRepository;
    private final OfferPhotoRepository offerPhotoRepository;
    private final OfferPhotoRepository offerRateRepository;
    private final EmployeeRepository employeeRepository;

    public OfferGetDTO offerToOfferGetDTO(Offer offer) {
        OfferGetDTO offerGetDTO = new OfferGetDTO();
        offerGetDTO.setId(offer.getId());
        offerGetDTO.setTitle(offer.getTitle());
        offerGetDTO.setDescription(offer.getDescription());
        offerGetDTO.setState(offer.getState());
        offerGetDTO.setDatePublished(offer.getDatePublished());

        offerGetDTO.setAuthorName(offer.getAuthor().getFirstName() + " "
                + offer.getAuthor().getLastName() + " "
                + offer.getAuthor().getFatherName());

        int likes = 0;
        int dislikes = 0;
        for (OfferRate rate : offer.getOfferRate()) {
            if (rate.getType() == OfferRateType.LIKE) {
                likes++;
            } else {
                dislikes++;
            }
        }
        offerGetDTO.setLikes(likes);
        offerGetDTO.setDislikes(dislikes);

        List<String> imagesList = new java.util.ArrayList<>(List.of());
        for (OfferPhoto offerPhoto : offer.getOfferPhoto()) {
            imagesList.add(new String(offerPhoto.getPhoto(), StandardCharsets.UTF_8));
        }
        offerGetDTO.setImages(imagesList);


        return offerGetDTO;

    }

    public Offer offerFromOfferPostAndPutDTO(OfferPostAndPutDTO offer) {
        Offer result = new Offer();

        result.setTitle(offer.getTitle());
        result.setDescription(offer.getDescription());
        result.setDatePublished(offer.getDatePublished());
        result.setState(OfferState.WAITING);
        result.setOfferPhoto(new LinkedHashSet<OfferPhoto>());
        result.setOfferRate(new LinkedHashSet<OfferRate>());
        result.setAuthor(employeeRepository.findById(offer.getAuthorId())
                .orElseThrow(() -> new EmployeeNotFoundException(offer.getAuthorId())));
        for (String photo : offer.getImages()) {
            OfferPhoto offerPhoto = new OfferPhoto();
            offerPhoto.setPhoto(photo.getBytes());
            result.getOfferPhoto().add(offerPhoto);
        }
        for (OfferPhoto photo : result.getOfferPhoto()) {
            photo.setOffer(result);
        }
        return result;
    }

    public OfferPostAndPutDTO offerToOfferPostAndPutDTO(Offer offer) {
        OfferPostAndPutDTO result = new OfferPostAndPutDTO();
        result.setTitle(offer.getTitle());
        result.setDescription(offer.getDescription());
        result.setDatePublished(offer.getDatePublished());

        result.setAuthorId(offer.getAuthor().getId());

        List<String> photoListForResult = new java.util.ArrayList<>(List.of());
        for (OfferPhoto photo : offer.getOfferPhoto()) {
            photoListForResult.add(new String(photo.getPhoto(), StandardCharsets.UTF_8));
        }
        result.setImages(photoListForResult);

        return result;
    }

    public OfferRatePutDTO offerRateToOfferRatePutDTO(OfferRate offerRate) {
        OfferRatePutDTO result = new OfferRatePutDTO();
        result.setType(offerRate.getType());
        result.setOfferTitle(offerRate.getOffer().getTitle());
        result.setOfferAuthorName(offerRate.getAuthor().getFirstName() + " " +
                offerRate.getAuthor().getLastName() + " " +
                offerRate.getAuthor().getFatherName()
        );
        return result;
    }
}
