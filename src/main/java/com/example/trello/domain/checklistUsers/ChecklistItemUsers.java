package com.example.trello.domain.checklistUsers;

import com.example.trello.domain.checklist.entity.ChecklistItem;
import com.example.trello.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class ChecklistItemUsers {
	@EmbeddedId
	private ChecklistItemUsersPK checklistItemUsersPK;

	@ManyToOne( fetch = FetchType.LAZY )
	@JoinColumn( name = "item_id" )
	@MapsId( "itemId" )
	private ChecklistItem checklistItem;

	@ManyToOne( fetch = FetchType.LAZY )
	@JoinColumn( name = "user_id" )
	@MapsId( "userId" )
	private User user;

	@Builder
	public ChecklistItemUsers( ChecklistItem checklistItem, User user ) {
		this.checklistItem = checklistItem;
		this.user = user;
		this.checklistItemUsersPK = ChecklistItemUsersPK.builder()
				.itemId( checklistItem.getId() )
				.userId( user.getId() )
				.build();
	}
}
