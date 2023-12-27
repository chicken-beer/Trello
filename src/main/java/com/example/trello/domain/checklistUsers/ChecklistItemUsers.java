package com.example.trello.domain.checklistUsers;

import com.example.trello.domain.checklist.entity.ChecklistItem;
import com.example.trello.domain.user.entity.User;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class ChecklistItemUsers {
	@EmbeddedId
	private ChecklistItemUsersPK checklistItemUsersPK;

	@ManyToOne( fetch = FetchType.LAZY )
	private ChecklistItem checklistItem;

	@ManyToOne( fetch = FetchType.LAZY )
	private User user;

	@Builder
	public ChecklistItemUsers( ChecklistItem checklistItem, User user ) {
		this.checklistItem = checklistItem;
		this.user = user;
	}
}
