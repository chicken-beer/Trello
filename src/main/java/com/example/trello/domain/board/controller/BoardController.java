package com.example.trello.domain.board.controller;

import com.example.trello.domain.board.dto.BoardRequestDto;
import com.example.trello.domain.board.service.BoardService;
import com.example.trello.global.response.ApiResponse;
import com.example.trello.global.security.UserDetailsImpl;
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
    public ResponseEntity<ApiResponse> createBoard(@RequestBody BoardRequestDto boardRequestDto,
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
                                                   @RequestBody BoardRequestDto boardRequestDto,
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
}
