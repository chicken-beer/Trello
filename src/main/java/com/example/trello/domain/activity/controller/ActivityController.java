package com.example.trello.domain.activity.controller;

import com.example.trello.domain.activity.dto.CommentRequestDto;
import com.example.trello.domain.activity.service.ActivityService;
import com.example.trello.global.response.ApiResponse;
import com.example.trello.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor

@RestController
@RequestMapping("/v1/boards/{boardId}/columns/{columnId}/cards/{cardId}/activities")
public class ActivityController {

    private final ActivityService activityService;

    @PostMapping
    public ResponseEntity<ApiResponse> postComment(
            @RequestBody CommentRequestDto requestDto,
            @PathVariable Long boardId,
            @PathVariable Long columnId,
            @PathVariable Long cardId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return ResponseEntity.ok(ApiResponse.ok(
                activityService.postComment(requestDto, boardId, columnId, cardId, userDetails)
        ));
    }

    @PatchMapping("{activityId}")
    public ResponseEntity<ApiResponse> updateComment(
            @RequestBody CommentRequestDto requestDto,
            @PathVariable Long boardId,
            @PathVariable Long columnId,
            @PathVariable Long cardId,
            @PathVariable Long activityId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return ResponseEntity.ok(ApiResponse.ok(
                activityService.updateComment(requestDto,boardId,columnId,cardId,activityId,userDetails)
        ));
    }

    @DeleteMapping("{activityId}")
    public ResponseEntity<ApiResponse> deleteComment(
            @PathVariable Long boardId,
            @PathVariable Long columnId,
            @PathVariable Long cardId,
            @PathVariable Long activityId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return ResponseEntity.ok(ApiResponse.ok(
                activityService.deleteComment(boardId,columnId,cardId,activityId,userDetails)
        ));
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getActivitiesFromCard(
            @PathVariable Long boardId,
            @PathVariable Long columnId,
            @PathVariable Long cardId
    ) {
        return ResponseEntity.ok(ApiResponse.ok(
                activityService.getActivitiesFromCard(boardId,columnId,cardId)
        ));
    }

}
