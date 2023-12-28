package com.example.trello.domain.checklist.repository;

import com.example.trello.domain.checklistUsers.ChecklistItemUsers;
import com.example.trello.domain.checklistUsers.ChecklistItemUsersPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChecklistItemUserRepository extends JpaRepository< ChecklistItemUsers, ChecklistItemUsersPK > {
}
