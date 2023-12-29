package com.example.trello.domain.activity.entity;

import com.example.trello.domain.activity.dto.CommentRequestDto;
import com.example.trello.domain.card.entity.Card;
import com.example.trello.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Activity {

    @Id
    @Column(name = "column_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String contents;
    private LocalDateTime createdDate;
    private Boolean isComment;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Activity(String contents, Card card, User addingUser) {
        this.contents = contents;
        this.createdDate = LocalDateTime.now();
        this.isComment = false;
        this.card = card;
        this.user = addingUser;
    }


    public Activity(CommentRequestDto requestDto, Card card, User user) {
        this.contents = requestDto.getComment();
        this.createdDate = LocalDateTime.now();
        this.isComment = true;
        this.card = card;
        this.user = user;

    }
}
