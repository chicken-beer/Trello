package com.example.trello.domain.label.dto;

import lombok.Builder;

@Builder
public class LabelResponseDto {
	private long id;
	private String name;
	private String color;
}
