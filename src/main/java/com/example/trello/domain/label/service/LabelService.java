package com.example.trello.domain.label.service;

import com.example.trello.domain.label.dto.LabelRequestDto;
import com.example.trello.domain.label.dto.LabelResponseDto;
import com.example.trello.domain.label.entity.Label;
import com.example.trello.domain.label.repository.LabelRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class LabelService {
	private final LabelRepository labelRepository;

	public List< LabelResponseDto > get_labels() {
		return labelRepository.findAll().stream().map( l -> LabelResponseDto.builder()
				.id( l.getId() )
				.name( l.getName() )
				.color( l.getColor() )
				.build() ).toList();
	}

	public void add_label( LabelRequestDto labelRequestDto ) {
		labelRepository.save( Label.builder()
				.name( labelRequestDto.getName() )
				.color( labelRequestDto.getColor() )
				.build() );
	}

	@Transactional
	public void modify_label( long id, LabelRequestDto labelRequestDto ) {
		var label = labelRepository.findById( id ).orElseThrow( () ->
				new NoSuchElementException( "label no such element" )
		);
		label.setName( labelRequestDto.getName() );
		label.setColor( labelRequestDto.getColor() );
	}

	public void delete_label( long id ) {
		labelRepository.deleteById( id );
	}
}
