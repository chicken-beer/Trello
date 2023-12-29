package com.example.trello.domain.cardUsers.entity;

import com.example.trello.domain.card.entity.Card;
import com.example.trello.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class CardUsers {

    @EmbeddedId
    private CardUsersPK cardUsersPK;

    @ManyToOne
    @JoinColumn(name = "card_id")
    @MapsId("cardId")
    private Card card;

    @ManyToOne
    @JoinColumn(name = "adding_user_id")
    @MapsId("addingUserId")
    private User addingUser;

    @ManyToOne
    @JoinColumn(name = "added_user_id")
    @MapsId("addedUserId")
    private User addedUser;


    public CardUsers(Card card, User user) {
        this.card = card;
        this.addedUser = user;
        this.addingUser = user;
        this.cardUsersPK = new CardUsersPK(card.getId(), user.getId(), user.getId());
    }
}

