package com.example.trello.domain.board.dto;

import com.example.trello.domain.board.entity.Board;
import lombok.Getter;

@Getter
public class BoardResponseDto {

    private String title;

    private String backImg;


    public BoardResponseDto(Board board) {
        this.title = board.getTitle();
        this.backImg = null;
    }
    public BoardResponseDto(Board board, String backImg) {
        this.title = board.getTitle();
        this.backImg = backImg;
    }
}
