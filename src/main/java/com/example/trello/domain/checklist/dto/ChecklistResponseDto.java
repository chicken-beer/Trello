package com.example.trello.domain.checklist.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ChecklistResponseDto {
	private long id;
	private String title;
}
