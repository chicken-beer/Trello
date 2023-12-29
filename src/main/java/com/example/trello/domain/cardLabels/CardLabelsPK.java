package com.example.trello.domain.cardLabels;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor
public class CardLabelsPK implements Serializable {
	@Column( name = "card_id" )
	private long cardId;
	@Column( name = "label_id" )
	private long labelId;

	@Builder
	public CardLabelsPK( long cardId, long labelId ) {
		this.cardId = cardId;
		this.labelId = labelId;
	}
}
