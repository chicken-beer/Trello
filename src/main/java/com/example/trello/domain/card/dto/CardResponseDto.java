package com.example.trello.domain.card.dto;

import com.example.trello.domain.card.entity.Card;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CardResponseDto {

    private final Long id;
    private final String title;
    private final String description;
    private final String coverImg;
    private final String fileURL;
    private final LocalDateTime dueDate;
    private final Boolean isArchived;

    public CardResponseDto(Card card, String fileURL) {
        this.id = card.getId();
        this.title = card.getTitle();
        this.description = card.getDescription();
        this.coverImg = card.getCoverImg();
        this.fileURL = fileURL;
        this.dueDate = card.getDueDate();
        this.isArchived = card.getIsArchived();
    }

    public CardResponseDto(Card card) {
        this.id = card.getId();
        this.title = card.getTitle();
        this.description = card.getDescription();
        this.coverImg = card.getCoverImg();
        this.fileURL = null;
        this.dueDate = card.getDueDate();
        this.isArchived = card.getIsArchived();
    }
}
