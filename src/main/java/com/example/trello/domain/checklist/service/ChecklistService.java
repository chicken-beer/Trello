package com.example.trello.domain.checklist.service;

import com.example.trello.domain.checklist.dto.ChecklistResponseDto;
import com.example.trello.domain.checklist.repository.ChecklistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChecklistService {
	private final ChecklistRepository checklistRepository;

	public List< ChecklistResponseDto > get_checklist() {
		return checklistRepository.findAll().stream().map( c -> ChecklistResponseDto.builder()
				.title( c.getTitle() )
				.build() ).toList();
	}
}
