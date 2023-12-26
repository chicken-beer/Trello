package com.example.trello.domain.label.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table( name = "Label" )
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
