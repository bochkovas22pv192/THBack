package com.example.THBack.dto;

import com.example.THBack.models.enums.OfferRateType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfferRatePutDTO {
    private OfferRateType type;
    private String offerAuthorName;
    private String offerTitle;
}
