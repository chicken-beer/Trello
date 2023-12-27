package com.example.trello.domain.card.controller;

import com.example.trello.domain.card.dto.CardRequestDto;
import com.example.trello.domain.card.dto.CardResponseDto;
import com.example.trello.domain.card.dto.CardTitleUpdateRequestDto;
import com.example.trello.domain.card.service.CardService;
import com.example.trello.global.CommonResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor

@RequestMapping("/v1/boards/{boardId}/columns/{columnId}/cards")
@RestController
public class CardController {

    private final CardService cardService;

    // 카드 작성
    @PostMapping
    public ResponseEntity<CommonResponseDto> postCard(
            @PathVariable Long boardId,
            @PathVariable Long columnId,
            @Valid @RequestBody CardRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        CommonResponseDto responseDto = cardService.postCard(boardId, columnId, requestDto, userDetails.getUser());

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    // 단일 카드 가져오기
    @GetMapping("/{cardId}")
    public ResponseEntity<CardResponseDto> getCard(
            @PathVariable Long boardId,
            @PathVariable Long columnId,
            @PathVariable Long cardId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        CardResponseDto responseDto = cardService.getCard(boardId, columnId, cardId);

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    // 카드 제목 수정
    @PatchMapping("/{cardId}/title")
    public ResponseEntity<CommonResponseDto> updateCardTitle(
            @PathVariable Long boardId,
            @PathVariable Long columnId,
            @PathVariable Long cardId,
            @Valid @RequestBody CardTitleUpdateRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        CommonResponseDto responseDto = cardService.updateCardTitle(boardId, columnId, cardId, requestDto, userDetails.getUser());

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    // 카드 숨기기
    @PatchMapping("/{cardId}/archive")
    public ResponseEntity<CommonResponseDto> toggleIsArchived(
            @PathVariable Long boardId,
            @PathVariable Long columnId,
            @PathVariable Long cardId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        CommonResponseDto responseDto = cardService.toggleIsArchived(boardId, columnId, cardId, userDetails.getUser());

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    // 카드 삭제
    @DeleteMapping("/{cardId}")
    public ResponseEntity<CommonResponseDto> deleteCard(
            @PathVariable Long boardId,
            @PathVariable Long columnId,
            @PathVariable Long cardId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        CommonResponseDto responseDto = cardService.deleteCard(boardId, columnId, cardId, userDetails.getUser());

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
}
