package com.example.trello.domain.checklist.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ChecklistResponseDto {
	private long id;
	private String title;
	private List< ChecklistItemResponseDto > items;
}
