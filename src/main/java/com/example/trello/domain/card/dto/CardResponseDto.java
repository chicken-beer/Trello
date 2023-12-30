package com.example.trello.domain.card.dto;

import com.example.trello.domain.card.entity.Card;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CardResponseDto {

    private final Long id;
    private final String title;
    private final String description;
    private final String coverImg;
    private final String filename;
    private final LocalDateTime dueDate;
    private final Boolean isArchived;

    public CardResponseDto(Card card) {
        this.id = card.getId();
        this.title = card.getTitle();
        this.description = card.getDescription();
        this.coverImg = card.getCoverImg();
        this.filename = card.getFilename();
        this.dueDate = card.getDueDate();
        this.isArchived = card.getIsArchived();
    }
}
