package com.example.trello.domain.board.controller;

import com.example.trello.domain.board.dto.BoardRequestDto;
import com.example.trello.domain.board.service.BoardService;
import com.example.trello.global.response.ApiResponse;
import com.example.trello.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("")
    public ResponseEntity<ApiResponse> createBoard(@Valid @ModelAttribute BoardRequestDto boardRequestDto,
                                                   @AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseEntity.ok(ApiResponse.ok(boardService.createBoard(boardRequestDto,userDetails.getUser())));
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<ApiResponse> getBoard(@PathVariable Long boardId){
        return ResponseEntity.ok(ApiResponse.ok(boardService.getBoard(boardId)));
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse> getBoardList(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseEntity.ok(ApiResponse.ok(boardService.getBoardList(userDetails.getUser())));
    }

    @PatchMapping("/{boardId}")
    public ResponseEntity<ApiResponse> updateBoard(@PathVariable Long boardId,
                                                   @Valid @ModelAttribute BoardRequestDto boardRequestDto,
                                                   @AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseEntity.ok(ApiResponse.ok(boardService.updateBoard(boardId,boardRequestDto,userDetails.getUser())));
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<ApiResponse> deleteBoard(@PathVariable Long boardId,
                                                   @AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseEntity.ok(ApiResponse.ok(boardService.deleteBoard(boardId,userDetails.getUser())));
    }

    @PostMapping("/{boardId}")
    public ResponseEntity<ApiResponse> inviteUser(@PathVariable Long boardId,
                                                  @RequestBody String[] usernameList,
                                                  @AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseEntity.ok(ApiResponse.ok(boardService.inviteUser(boardId, usernameList,userDetails.getUser())));
    }

    @PatchMapping("/{boardId}/response")
    public ResponseEntity<ApiResponse> responseInvite(@PathVariable Long boardId,
                                                      @RequestParam("response") String response,
                                                      @AuthenticationPrincipal UserDetailsImpl userDetails){

        boardService.responseInvite(boardId,response,userDetails.getUser());
        return ResponseEntity.ok(ApiResponse.ok("초대 응답 완료"));
    }

    @PatchMapping("/{boardId}/users/{userId}")
    public ResponseEntity<ApiResponse> changeRole(@PathVariable Long boardId,
                                                  @PathVariable Long userId,
                                                  @RequestParam("role") String userRole,
                                                  @AuthenticationPrincipal UserDetailsImpl userDetails){
        boardService.changeUserRole(boardId,userId,userRole,userDetails.getUser());
        return ResponseEntity.ok(ApiResponse.ok("권한 변경 성공"));
    }

}
