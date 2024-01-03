package com.example.trello.domain.label.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Label {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private long id;
	private String name;
	private String color;

	@Builder
	public Label( String name, String color ) {
		this.name = name;
		this.color = color;
	}
}
