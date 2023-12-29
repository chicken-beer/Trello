package com.example.trello.domain.checklist.service;

import com.example.trello.domain.checklist.dto.ChecklistItemRequestDto;
import com.example.trello.domain.checklist.dto.ChecklistItemResponseDto;
import com.example.trello.domain.checklist.dto.ChecklistRequestDto;
import com.example.trello.domain.checklist.dto.ChecklistResponseDto;
import com.example.trello.domain.checklist.entity.ChecklistItem;
import com.example.trello.domain.checklist.repository.ChecklistItemRepository;
import com.example.trello.domain.checklist.repository.ChecklistItemUserRepository;
import com.example.trello.domain.checklist.repository.ChecklistRepository;
import com.example.trello.domain.checklistUsers.ChecklistItemUsers;
import com.example.trello.domain.checklistUsers.ChecklistItemUsersPK;
import com.example.trello.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ChecklistService {
	private final ChecklistRepository checklistRepository;
	private final ChecklistItemRepository checklistItemRepository;
	private final ChecklistItemUserRepository checklistItemUserRepository;
	private final UserRepository userRepository;

	private ChecklistItem check_ids( long checklistId, long itemId ) {
		checklistRepository.findById( checklistId ).orElseThrow( () ->
				new NoSuchElementException( "checklist를 찾을 수 없습니다. ID : " + checklistId )
		);

		return checklistItemRepository.findById( itemId ).orElseThrow( () ->
				new NoSuchElementException( "checklist item을 찾을 수 없습니다. ID : " + itemId )
		);
	}

	public List< ChecklistResponseDto > get_checklist() {
		return checklistRepository.findAll().stream().map( list -> ChecklistResponseDto.builder()
				.title( list.getTitle() )
				.card_id( list.getCard().getId() )
				.items(
						checklistItemRepository.findAllByChecklistId( list.getId() )
								.stream().map( item -> ChecklistItemResponseDto.builder()
										.title( item.getTitle() )
										.isChecked( item.isChecked() )
										.dueDate( item.getDueDate() )
										.build()
								).toList()
				)
				.build()
		).toList();
	}

	@Transactional
	public void modify_title( long checklistId, ChecklistRequestDto checklistRequestDto ) {
		var item = checklistRepository.findById( checklistId ).orElseThrow( () ->
				new NoSuchElementException( "checklist를 찾을 수 없습니다 ID : " + checklistId )
		);

		item.setTitle( checklistRequestDto.getTitle() );
	}

	public void add_item( long checklistId, ChecklistItemRequestDto checklistItemRequestDto ) {
		var checklist = checklistRepository.findById( checklistId ).orElseThrow(
				() -> new NoSuchElementException( "해당 checklist가 존재하지 않습니다. ID : " + checklistId )
		);

		checklistItemRepository.save( ChecklistItem.builder()
				.title( checklistItemRequestDto.getTitle() )
				.due_date( checklistItemRequestDto.getDueDate() )
				.isChecked( false )
				.checklist( checklist )
				.build()
		);
	}

	public void delete_item( long checklistId, long itemId ) {
		var item = check_ids( checklistId, itemId );
		checklistItemRepository.delete( item );
	}

	@Transactional
	public void modify_item_title( long checklistId, long itemId, ChecklistItemRequestDto checklistItemRequestDto ) {
		var item = check_ids( checklistId, itemId );
		item.setTitle( checklistItemRequestDto.getTitle() );
	}

	@Transactional
	public void add_modify_item_due_date( long checklistId, long itemId, ChecklistItemRequestDto checklistItemRequestDto ) {
		var item = check_ids( checklistId, itemId );
		item.setDueDate( checklistItemRequestDto.getDueDate() );
	}

	@Transactional
	public void delete_item_due_date( long checklistId, long itemId ) {
		var item = check_ids( checklistId, itemId );
		item.setDueDate( null );
	}

	@Transactional
	public void add_item_user( long checklistId, long itemId, ChecklistItemRequestDto checklistItemRequestDto ) {
		var item = check_ids( checklistId, itemId );
		var user = userRepository.findById( checklistItemRequestDto.getUserId() ).orElseThrow( () ->
				new NoSuchElementException( "사용자를 찾을 수 없습니다. ID : " + checklistItemRequestDto.getUserId() )
		);

		checklistItemUserRepository.save( ChecklistItemUsers.builder()
				.checklistItem( item )
				.user( user )
				.build() );
	}

	public void delete_item_user( long checklistId, long itemId, long userId ) {
		check_ids( checklistId, itemId );
		checklistItemUserRepository.deleteById( ChecklistItemUsersPK.builder()
				.itemId( itemId )
				.userId( userId )
				.build()
		);
	}

	@Transactional
	public void checked_item( long checklistId, long itemId, ChecklistItemRequestDto checklistItemRequestDto ) {
		var item = check_ids( checklistId, itemId );
		item.setChecked( checklistItemRequestDto.getIsChecked() );
	}
}
