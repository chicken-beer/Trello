package com.example.trello.domain.card.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CardTitleUpdateRequestDto {

    @NotBlank
    private String title;
}
