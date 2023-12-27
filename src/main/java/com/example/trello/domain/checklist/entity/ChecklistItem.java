package com.example.trello.domain.checklist.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Entity
@Setter
@NoArgsConstructor
public class ChecklistItem {
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private long id;
	private String title;
	private LocalDateTime due_date;
	private boolean is_checked;

	@ManyToOne(fetch = FetchType.LAZY)
	private Checklist checklist;

	@Builder
	public ChecklistItem( String title, LocalDateTime due_date, boolean is_checked ) {
		this.title = title;
		this.due_date = due_date;
		this.is_checked = is_checked;
	}
}
