package com.example.trello.domain.board.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class BoardRequestDto {

    @Size(min = 1 , max = 12, message = "보드명을 1~12자로 작성해주세요.")
    private String title;

    private String backImg;

    @Size(max = 100 ,message = "100자 이내로 작성해주세요.")
    private String description;

}
