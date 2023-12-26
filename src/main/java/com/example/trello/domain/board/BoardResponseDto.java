package com.example.trello.domain.board;

import lombok.Getter;

import java.util.Optional;

@Getter
public class BoardResponseDto {

    private String title;

    private String backImg;

    public BoardResponseDto(Board board) {
        this.title = board.getTitle();
        this.backImg = board.getBackImg();
    }
}
