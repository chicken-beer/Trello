package com.example.trello.domain.boardUsers;

import com.example.trello.domain.board.Board;
import com.example.trello.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardUsers{

    @EmbeddedId
    private BoardUsersPK boardUsersPK;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    @MapsId("boardId")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @MapsId("userId")
    private User user;

    @Builder
    public BoardUsers(Board board, User user){
        this.board = board;
        this.user = user;
        this.boardUsersPK = BoardUsersPK.builder()
                .boardId(board.getId())
                .userId(user.getId())
                .build();
    }
}
