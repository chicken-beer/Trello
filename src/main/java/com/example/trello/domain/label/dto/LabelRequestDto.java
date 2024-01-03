package com.example.trello.domain.label.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LabelRequestDto {
	private String name;
	private String color;
}
