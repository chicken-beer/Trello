package com.example.trello.domain.checklist.repository;

import com.example.trello.domain.checklist.entity.ChecklistItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChecklistItemRepository extends JpaRepository< ChecklistItem, Long > {
	public List< ChecklistItem > findAllByChecklistId( long checklistId );
}
