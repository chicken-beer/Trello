package com.example.trello.domain.column.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateColumnRequestDto {

    private String name;
    private Boolean isArchived;
}
