package com.example.trello.domain.label.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LabelResponseDto {
	private long id;
	private String name;
	private String color;
}
