package com.example.trello.domain.card.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CardDueDateUpdateRequestDto {

    private LocalDateTime dueDate;
}
