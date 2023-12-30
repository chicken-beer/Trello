package com.example.trello.domain.card.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@RequiredArgsConstructor
public class CardFileRequestDto {

    private final MultipartFile File;
}
