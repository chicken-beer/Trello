package com.example.trello.domain.card.controller;

import com.example.trello.domain.card.dto.*;
import com.example.trello.domain.card.service.CardService;
import com.example.trello.global.response.ApiResponse;
import com.example.trello.global.response.CommonResponseDto;
import com.example.trello.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor

@RequestMapping("/v1/boards/{boardId}/columns/{columnId}/cards")
@RestController
public class CardController {

    private final CardService cardService;

    // 카드 상세 보기
    @GetMapping("/{cardId}")
    public ResponseEntity<ApiResponse> getCard(
            @PathVariable Long boardId,
            @PathVariable Long columnId,
            @PathVariable Long cardId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        CardResponseDto responseDto = cardService.getCard(boardId, columnId, cardId);

        return ResponseEntity.ok( ApiResponse.ok(responseDto) );
    }

    // 카드 생성
    @PostMapping
    public ResponseEntity<ApiResponse> postCard(
            @PathVariable Long boardId,
            @PathVariable Long columnId,
            @Valid @ModelAttribute CardRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        CommonResponseDto responseDto = cardService.postCard(boardId, columnId, requestDto, userDetails.getUser());

        return ResponseEntity.ok( ApiResponse.ok(responseDto) );
    }

    // 카드 제목 수정
    @PatchMapping("/{cardId}/title")
    public ResponseEntity<ApiResponse> updateCardTitle(
            @PathVariable Long boardId,
            @PathVariable Long columnId,
            @PathVariable Long cardId,
            @Valid @RequestBody CardTitleUpdateRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        CommonResponseDto responseDto = cardService.updateCardTitle(boardId, columnId, cardId, requestDto, userDetails.getUser());

        return ResponseEntity.ok( ApiResponse.ok(responseDto) );
    }

    // 카드 종료 기한 설정
    @PatchMapping("/{cardId}/duedate")
    public ResponseEntity<ApiResponse> updateDueDate(
            @PathVariable Long boardId,
            @PathVariable Long columnId,
            @PathVariable Long cardId,
            @RequestBody CardDueDateUpdateRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return ResponseEntity.ok( ApiResponse.ok(cardService.updateDueDate(boardId, columnId, cardId, requestDto, userDetails.getUser())) );

    }

    @PatchMapping("/{cardId}/file")
    public ResponseEntity<ApiResponse> updateFile(
            @PathVariable Long boardId,
            @PathVariable Long columnId,
            @PathVariable Long cardId,
            @ModelAttribute CardFileRequestDto cardFileRequestDto
    ){
        return ResponseEntity.ok(ApiResponse.ok(cardService.updateFile(boardId,columnId,cardId,cardFileRequestDto)));

    }

    // 카드 삭제
    @DeleteMapping("/{cardId}")
    public ResponseEntity<ApiResponse> deleteCard(
            @PathVariable Long boardId,
            @PathVariable Long columnId,
            @PathVariable Long cardId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        CommonResponseDto responseDto = cardService.deleteCard(boardId, columnId, cardId, userDetails.getUser());

        return ResponseEntity.ok( ApiResponse.ok(responseDto) );
    }

    // 카드 숨기기
    @PatchMapping("/{cardId}/archive")
    public ResponseEntity<ApiResponse> toggleIsArchived(
            @PathVariable Long boardId,
            @PathVariable Long columnId,
            @PathVariable Long cardId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        CommonResponseDto responseDto = cardService.toggleIsArchived(boardId, columnId, cardId, userDetails.getUser());

        return ResponseEntity.ok( ApiResponse.ok(responseDto) );
    }

    // 카드에 멤버 추가
    @PostMapping("/{cardId}/users/{userId}")
    public ResponseEntity<ApiResponse> addUserToCard(
            @PathVariable Long boardId,
            @PathVariable Long columnId,
            @PathVariable Long cardId,
            @PathVariable Long userId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return ResponseEntity.ok( ApiResponse.ok(cardService.addUserToCard(boardId, columnId, cardId, userId, userDetails.getUser()) ));
    }

    // 카드에 멤버 삭제
    @DeleteMapping("/{cardId}/users/{userId}")
    public ResponseEntity<ApiResponse> deleteUserFromCard(
            @PathVariable Long boardId,
            @PathVariable Long columnId,
            @PathVariable Long cardId,
            @PathVariable Long userId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return ResponseEntity.ok( ApiResponse.ok(cardService.deleteUserFromCard(boardId, columnId, cardId, userId, userDetails.getUser()) ));
    }

    // 카드 순서 변경
    @PatchMapping("/{cardId}/order/{cardOrder}")
    public ResponseEntity<ApiResponse> updateCardOrder(@PathVariable Long boardId,
                                                         @PathVariable Long columnId,
                                                         @PathVariable Long cardId,
                                                         @PathVariable Integer cardOrder) {
        cardService.updateCardOrder(boardId,columnId,cardId, cardOrder);
        return ResponseEntity.ok( ApiResponse.ok( "카드 순서 수정 성공" ) );
    }

    // 카드 컬럼 이동
    @PatchMapping("/{cardId}")
    public ResponseEntity<ApiResponse> moveCardToAnotherColumns(@PathVariable Long boardId,
                                                         @PathVariable Long columnId,
                                                         @PathVariable Long cardId) {
        cardService.moveCardToAnotherColumns(boardId,columnId,cardId);
        return ResponseEntity.ok( ApiResponse.ok( "카드 이동 수정 성공" ) );
    }


}
