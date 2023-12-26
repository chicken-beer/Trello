package com.example.trello.domain.label.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table( name = "Label" )
public class Label {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private long id;
	private String name;
	private String color;
}
