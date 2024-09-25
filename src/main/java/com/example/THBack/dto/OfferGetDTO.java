package com.example.THBack.dto;

import com.example.THBack.models.Employee;
import com.example.THBack.models.enums.OfferState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfferGetDTO {
    private Long id;
    private String title;
    private String description;
    private OfferState state;
    private String authorName;
    private LocalDate datePublished;
    private int likes;
    private int dislikes;
    private List<String> images;
}
