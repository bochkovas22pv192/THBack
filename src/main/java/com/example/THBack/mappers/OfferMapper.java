package com.example.THBack.mappers;


import com.example.THBack.dto.OfferGetDTO;
import com.example.THBack.models.Offer;
import com.example.THBack.models.OfferPhoto;
import com.example.THBack.models.OfferRate;
import com.example.THBack.models.enums.OfferRateType;
import com.example.THBack.repository.OfferPhotoRepository;
import com.example.THBack.repository.OfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OfferMapper {

    private final OfferRepository offerRepository;
    private final OfferPhotoRepository offerPhotoRepository;
    private final OfferPhotoRepository offerRateRepository;
    private final OfferPhotoRepository EmployeeRepository;

    public OfferGetDTO ToOfferGetDTO(Offer offer){
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
        for(OfferRate rate : offer.getOfferRate()){
            if (rate.getType() == OfferRateType.LIKE){
                likes++;
            }
            else {
                dislikes++;
            }
        }
        offerGetDTO.setLikes(likes);
        offerGetDTO.setDislikes(dislikes);

        List<byte[]> imagesList = new java.util.ArrayList<>(List.of());
        for (OfferPhoto offerPhoto : offer.getOfferPhoto()){
            imagesList.add(offerPhoto.getPhoto());
        }
        offerGetDTO.setImages(imagesList);


        return offerGetDTO;

    }
}
