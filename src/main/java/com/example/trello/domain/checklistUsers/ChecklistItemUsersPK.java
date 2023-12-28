package com.example.trello.domain.checklistUsers;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor
public class ChecklistItemUsersPK implements Serializable {
	@Column( name = "item_id" )
	private long itemId;

	@Column( name = "user_id" )
	private long userId;

	@Builder
	public ChecklistItemUsersPK( long itemId, long userId ) {
		this.itemId = itemId;
		this.userId = userId;
	}
}
