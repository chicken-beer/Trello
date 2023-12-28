package com.example.trello.domain.card.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CardRequestDto {
    @NotBlank
    private String title;
    private String description;
    private String coverImg;
    private Boolean isArchived;
}