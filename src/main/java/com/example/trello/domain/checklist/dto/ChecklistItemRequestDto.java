package com.example.trello.domain.checklist.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ChecklistItemRequestDto {
	private String title;
	private LocalDateTime dueDate;
	private Boolean isChecked;
	private long userId;
}
