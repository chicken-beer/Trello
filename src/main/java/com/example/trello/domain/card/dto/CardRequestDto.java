package com.example.trello.domain.card.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CardRequestDto {
    // trello에서 제목을 제외하고는 값이 들어가지 않아도 카드 생성이 됩니다.
    @NotBlank
    private String title;
    private String description;
    private String coverImg;
    private LocalDateTime dueDate;
    private Boolean isArchived;
}
