package com.example.trello.domain.checklist.repository;

import com.example.trello.domain.checklist.entity.Checklist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChecklistRepository extends JpaRepository< Checklist, Long > {
}
