package com.example.trello.domain.boardUsers.controller;

import com.example.trello.domain.board.entity.Board;
import com.example.trello.domain.boardUsers.service.BoardUsersService;
import com.example.trello.global.response.ApiResponse;
import com.example.trello.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/boardUsers")
@RequiredArgsConstructor
public class BoardUsersController {

    private final BoardUsersService boardUsersService;

    @PostMapping("/{boardId}")
    public ResponseEntity<ApiResponse> inviteUser(@PathVariable Long boardId,
                                                  @RequestBody String[] usernameList,
                                                  @AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseEntity.ok(ApiResponse.ok(boardUsersService.inviteUser(boardId, usernameList,userDetails.getUser())));
    }

    @PatchMapping("/{boardId}/response")
    public ResponseEntity<ApiResponse> responseInvite(@PathVariable Long boardId,
                                                      @RequestParam("response") String response,
                                                      @AuthenticationPrincipal UserDetailsImpl userDetails){

        boardUsersService.responseInvite(boardId,response,userDetails.getUser());
        return ResponseEntity.ok(ApiResponse.ok("초대 응답 완료"));
    }

    @PatchMapping("/{boardId}/users/{userId}")
    public ResponseEntity<ApiResponse> changeRole(@PathVariable Long boardId,
                                                  @PathVariable Long userId,
                                                  @RequestParam("role") String userRole,
                                                  @AuthenticationPrincipal UserDetailsImpl userDetails){
        boardUsersService.changeUserRole(boardId,userId,userRole,userDetails.getUser());
        return ResponseEntity.ok(ApiResponse.ok("권한 변경 성공"));
    }
}
