package com.example.trello.domain.column.dto;

import com.example.trello.domain.card.dto.CardResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ColumnResponseDto {

    private String name;

    private List<CardResponseDto> cards = new ArrayList<>();

}
