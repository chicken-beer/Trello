package com.example.trello.domain.cardUsers.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class CardUsersPK implements Serializable {

    @Column(name = "card_id")
    private Long cardId;

    @Column(name = "adding_user_id")
    private Long addingUserId;

    @Column(name = "added_user_id")
    private Long addedUserId;

}
