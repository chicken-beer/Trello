package com.example.trello.domain.board.entity;

import com.example.trello.domain.board.dto.BoardRequestDto;
import com.example.trello.domain.boardUsers.entity.BoardUsers;
import com.example.trello.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String creator;

    @Column(nullable = false)
    private String title;

    @Column
    private String backImg;

    @Column
    private String description;

    @OneToMany(mappedBy = "board" , cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoardUsers> boardUserList = new ArrayList<>();

    public Board(BoardRequestDto boardRequestDto, User user) {
        this.creator = user.getLoginId();
        this.title = boardRequestDto.getTitle();
        this.backImg = boardRequestDto.getBackImg();
        this.description = boardRequestDto.getDescription();
    }

    public void update(BoardRequestDto boardRequestDto) {
        this.title = boardRequestDto.getTitle();
        this.backImg = boardRequestDto.getBackImg();
        this.description = boardRequestDto.getDescription();
    }

}
