package com.example.trello.domain.cardLabels;

import com.example.trello.domain.card.entity.Card;
import com.example.trello.domain.label.entity.Label;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class CardLabels {
	@EmbeddedId
	private CardLabelsPK cardLabelsPK;

	@ManyToOne( fetch = FetchType.LAZY )
	@JoinColumn( name = "card_id" )
	@MapsId( "cardId" )
	private Card card;

	@ManyToOne( fetch = FetchType.LAZY )
	@JoinColumn( name = "label_id" )
	@MapsId( "labelId" )
	private Label label;

	@Builder
	public CardLabels( Card card, Label label ) {
		this.card = card;
		this.label = label;
		this.cardLabelsPK = CardLabelsPK.builder()
				.cardId( card.getId() )
				.labelId( label.getId() )
				.build();
	}
}
