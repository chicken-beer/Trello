package com.example.trello.domain.checklist.controller;

import com.example.trello.domain.checklist.dto.ChecklistItemRequestDto;
import com.example.trello.domain.checklist.dto.ChecklistRequestDto;
import com.example.trello.domain.checklist.service.ChecklistService;
import com.example.trello.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping( "/v1/checklist" )
public class ChecklistController {
	private final ChecklistService checklistService;

	@PatchMapping( "/{checklistId}/title" )
	public ResponseEntity< ApiResponse > modify_title(
			@PathVariable long checklistId,
			@RequestBody ChecklistRequestDto checklistRequestDto )
	{
		checklistService.modify_title( checklistId, checklistRequestDto );
		return ResponseEntity.ok( ApiResponse.ok( "modify success" ) );
	}

	@PostMapping( "/{checklistId}/items" )
	public ResponseEntity< ApiResponse > add_item(
			@PathVariable long checklistId,
			@RequestBody ChecklistItemRequestDto checklistItemRequestDto ) {
		checklistService.add_item( checklistId, checklistItemRequestDto );
		return ResponseEntity.ok( ApiResponse.ok( "add item success" ) );
	}

	@DeleteMapping( "/{checklistId}/items/{itemId}" )
	public ResponseEntity< ApiResponse > delete_item( @PathVariable long checklistId, @PathVariable long itemId ) {
		checklistService.delete_item( checklistId, itemId );
		return ResponseEntity.ok( ApiResponse.ok( "delete item success" ) );
	}

	@PatchMapping( "/{checklistId}/items/{itemId}/title" )
	public ResponseEntity< ApiResponse > modity_item_title(
			@PathVariable long checklistId,
			@PathVariable long itemId,
			@RequestBody ChecklistItemRequestDto checklistItemRequestDto )
	{
		checklistService.modify_item_title( checklistId, itemId, checklistItemRequestDto );
		return ResponseEntity.ok( ApiResponse.ok( "modify item title success" ) );
	}

	@PostMapping( "/{checklistId}/items/{itemId}/duedate" )
	public ResponseEntity< ApiResponse > add_item_due_date(
			@PathVariable long checklistId,
			@PathVariable long itemId,
			@RequestBody ChecklistItemRequestDto checklistItemRequestDto )
	{
		checklistService.add_modify_item_due_date( checklistId, itemId, checklistItemRequestDto );
		return ResponseEntity.ok( ApiResponse.ok( "add item due date success" ) );
	}

	@PatchMapping( "/{checklistId}/items/{itemId}/duedate" )
	public ResponseEntity< ApiResponse > modify_item_due_date(
			@PathVariable long checklistId,
			@PathVariable long itemId,
			@RequestBody ChecklistItemRequestDto checklistItemRequestDto )
	{
		checklistService.add_modify_item_due_date( checklistId, itemId, checklistItemRequestDto );
		return ResponseEntity.ok( ApiResponse.ok( "modify item due date success" ) );
	}


	@DeleteMapping( "/{checklistId}/items/{itemId}/duedate" )
	public ResponseEntity< ApiResponse > delete_item_due_date(
			@PathVariable long checklistId,
			@PathVariable long itemId )
	{
		checklistService.delete_item_due_date( checklistId, itemId );
		return ResponseEntity.ok( ApiResponse.ok( "delete item due date success" ) );
	}


	@PostMapping( "/{checklistId}/items/{itemId}/users" )
	public ResponseEntity< ApiResponse > add_item_users(
			@PathVariable long checklistId,
			@PathVariable long itemId,
			@RequestBody ChecklistItemRequestDto checklistItemRequestDto )
	{
		checklistService.add_item_user( checklistId, itemId, checklistItemRequestDto );
		return ResponseEntity.ok( ApiResponse.ok( "add item user success" ) );
	}

	@DeleteMapping( "/{checklistId}/items/{itemId}/users/{userId}" )
	public ResponseEntity< ApiResponse > delete_item_user(
			@PathVariable long checklistId,
			@PathVariable long itemId,
			@PathVariable long userId )
	{
		checklistService.delete_item_user( checklistId, itemId, userId );
		return ResponseEntity.ok( ApiResponse.ok( "delete item user success" ) );
	}

	@PatchMapping( "/{checklistId}/items/{itemId}/checked" )
	public ResponseEntity< ApiResponse > checked_item(
			@PathVariable long checklistId,
			@PathVariable long itemId,
			@RequestBody ChecklistItemRequestDto checklistItemRequestDto )
	{
		checklistService.checked_item( checklistId, itemId, checklistItemRequestDto );
		return ResponseEntity.ok( ApiResponse.ok( "item check success" ) );
	}
}
