package com.example.trello.domain.label.controller;

import com.example.trello.domain.label.dto.LabelRequestDto;
import com.example.trello.domain.label.dto.LabelResponseDto;
import com.example.trello.domain.label.service.LabelService;
import com.example.trello.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping( "/v1/labels" )
public class LabelController {
	private final LabelService labelService;

	@GetMapping
	public ResponseEntity< ApiResponse > get_labels() {
		return ResponseEntity.ok( ApiResponse.ok( labelService.get_labels() ) );
	}

	@PostMapping
	public ResponseEntity< ApiResponse > add_label( LabelRequestDto labelRequestDto ) {
		labelService.add_label( labelRequestDto );
		return ResponseEntity.ok( ApiResponse.ok( "add success" ) );
	}

	@PatchMapping( "/{labelId}" )
	public ResponseEntity< ApiResponse > modify_label( @PathVariable long labelId, @RequestBody LabelRequestDto labelRequestDto ) {
		labelService.modify_label( labelId, labelRequestDto );
		return ResponseEntity.ok( ApiResponse.ok( "modify success" ) );
	}

	@DeleteMapping( "/{labelId}" )
	public ResponseEntity< ApiResponse > delete_label( @PathVariable long labelId ) {
		labelService.delete_label( labelId );
		return ResponseEntity.ok( ApiResponse.ok( "delete success" ) );
	}
}
