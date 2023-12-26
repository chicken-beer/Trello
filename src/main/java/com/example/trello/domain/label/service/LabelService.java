package com.example.trello.domain.label.service;

import com.example.trello.domain.label.dto.LabelResponseDto;
import com.example.trello.domain.label.repository.LabelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LabelService {
	private final LabelRepository labelRepository;

	public List< LabelResponseDto > get_labels() {
		return null;
	}

}
