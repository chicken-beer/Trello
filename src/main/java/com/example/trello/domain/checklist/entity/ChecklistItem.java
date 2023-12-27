package com.example.trello.domain.checklist.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class ChecklistItem {
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private long id;
	private String title;
	private LocalDateTime dueDate;
	private boolean isChecked;

	@ManyToOne(fetch = FetchType.LAZY)
	private Checklist checklist;

	@Builder
	public ChecklistItem( String title, LocalDateTime due_date, boolean isChecked, Checklist checklist ) {
		this.title = title;
		this.dueDate = due_date;
		this.isChecked = isChecked;
		this.checklist = checklist;
	}
}
