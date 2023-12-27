package com.example.trello.domain.boardUsers.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor
public class BoardUsersPK implements Serializable {

    @Column(name = "board_id")
    private Long boardId;

    @Column(name = "user_id")
    private Long userId;

    @Builder
    public BoardUsersPK(Long boardId, Long userId){
        this.boardId = boardId;
        this.userId = userId;
    }
}
