package com.example.trello.domain.checklist.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
public class ChecklistItem {
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private long id;
	private LocalDateTime due_date;
	private boolean is_checked;
}
