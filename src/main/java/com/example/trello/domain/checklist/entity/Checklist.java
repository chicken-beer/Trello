package com.example.trello.domain.checklist.entity;

import com.example.trello.domain.card.entity.Card;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Checklist {
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private long id;
	private String title;

	@ManyToOne(fetch = FetchType.LAZY)
	private Card card;

	@Builder
	public Checklist( String title ) {
		this.title = title;
	}
}
