package com.example.trello.domain.boardUsers.entity;

import com.example.trello.domain.board.entity.Board;
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

    @Column(nullable = false)
    private String userRole;

    @Builder
    public BoardUsers(Board board, User user, String userRole){
        this.board = board;
        this.user = user;
        this.userRole = userRole;
        this.boardUsersPK = BoardUsersPK.builder()
                .boardId(board.getId())
                .userId(user.getId())
                .build();
    }

    public void updateUserRole(String userRole) {
        this.userRole = userRole;
    }
}
