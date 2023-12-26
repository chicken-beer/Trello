package com.example.trello.domain.card.controller;

import com.example.trello.domain.card.dto.CardRequestDto;
import com.example.trello.domain.card.dto.CardResponseDto;
import com.example.trello.domain.card.service.CardService;
import com.example.trello.global.CommonResponseDto;
import jakarta.validation.Valid;
import lombok.NoArgsConstructor;
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

    @GetMapping("/{cardId}")
    public ResponseEntity<CardResponseDto> getCard(
            @PathVariable Long boardId,
            @PathVariable Long columnId,
            @PathVariable Long cardId
    ) {
        CardResponseDto responseDto = cardService.getCard(boardId, columnId, cardId);

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @PatchMapping("/{cardId}")
    public ResponseEntity<CommonResponseDto> updateCard(
            @PathVariable Long boardId,
            @PathVariable Long columnId,
            @PathVariable Long cardId,
            @Valid @RequestBody CardRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        CommonResponseDto responseDto = cardService.updateCard(boardId, columnId, cardId, requestDto, userDetails.getUser());

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @DeleteMapping("/{cardId}")
    public ResponseEntity<CommonResponseDto> deleteCard(
            @PathVariable Long boardId,
            @PathVariable Long columnId,
            @PathVariable Long cardId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        CommonResponseDto responseDto = cardService.deleteCard(boardId, columnId, cardId, userDetails.getUser());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(responseDto);
    }
}
