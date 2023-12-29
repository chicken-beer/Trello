package com.example.trello.domain.activity.dto;

import com.example.trello.domain.activity.entity.Activity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ActivityResponseDto {
    private String contents;
    private LocalDateTime createdDate;
    private Boolean isComment;
    private String username;

    public ActivityResponseDto(Activity activity) {
        this.contents = activity.getContents();
        this.createdDate = activity.getCreatedDate();
        this.isComment = activity.getIsComment();
        this.username = activity.getUser().getUsername();
    }
}
