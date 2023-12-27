package com.example.trello.domain.checklist.repository;

import com.example.trello.domain.checklist.entity.ChecklistItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChecklistItemRepository extends JpaRepository< ChecklistItem, Long > {
}
