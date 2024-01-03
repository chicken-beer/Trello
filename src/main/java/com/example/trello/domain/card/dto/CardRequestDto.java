package com.example.trello.domain.card.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class CardRequestDto {
    @NotBlank
    private String title;
    private String description;
    private String coverImg;
    private MultipartFile file;
    private Boolean isArchived;
}