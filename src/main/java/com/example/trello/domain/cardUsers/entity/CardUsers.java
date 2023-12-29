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

    @Id
    @Column(name = "card_user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;

    @ManyToOne
    @JoinColumn(name = "adding_user_id")
    private User addingUser;

    @ManyToOne
    @JoinColumn(name = "added_user_id")
    private User addedUser;


    public CardUsers(Card card, User addingUser, User addedUser) {
        this.card = card;
        this.addedUser = addingUser;
        this.addingUser = addedUser;
    }
}

