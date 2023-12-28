package com.example.trello.domain.checklist.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ChecklistItemResponseDto {
	private String title;
	private LocalDateTime dueDate;
	private Boolean isChecked;
}
