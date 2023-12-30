package com.example.trello.domain.board.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class BoardRequestDto {

    @Size(min = 1 , max = 12, message = "보드명을 1~12자로 작성해주세요.")
    private String title;

    private MultipartFile backImg;

    @Size(max = 100 ,message = "100자 이내로 작성해주세요.")
    private String description;

}
