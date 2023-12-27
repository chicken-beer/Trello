package com.example.trello.domain.checklist.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Checklist {
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private long id;
	private String title;

	@Builder
	public Checklist( String title ) {
		this.title = title;
	}
}
