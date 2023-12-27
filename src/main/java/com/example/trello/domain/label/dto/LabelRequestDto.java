package com.example.trello.domain.label.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class LabelRequestDto {
	private String name;
	private String color;
}
