package com.example.trello.domain.activity.service;

import com.example.trello.domain.activity.dto.CommentRequestDto;
import com.example.trello.domain.activity.repository.ActivityRepository;
import com.example.trello.domain.activity.entity.Activity;
import com.example.trello.domain.board.repository.BoardRepository;
import com.example.trello.domain.card.entity.Card;
import com.example.trello.domain.card.repository.CardRepository;
import com.example.trello.domain.column.repository.ColumnRepository;
import com.example.trello.domain.user.entity.User;
import com.example.trello.domain.user.repository.UserRepository;
import com.example.trello.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivityService extends ActivityContents {

    private final ActivityRepository activityRepository;
    private final UserRepository userRepository;
    private final CardRepository cardRepository;
    private final BoardRepository boardRepository;
    private final ColumnRepository columnRepository;


    public void makeActivityByAddUser(Long cardId, Long userId, UserDetailsImpl userDetails) {
        Card card = cardRepository.findById(cardId).orElseThrow(
                () -> new IllegalArgumentException("aop, search by cardId. 없을리가 없습니다."));
        User addedUser = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("aop, search by userId. 없을리가 없습니다."));
        User addingUser = userDetails.getUser();

        String contents = "";
        if (addingUser.equals(addedUser)) {
            contents = ADDUSERSELF(addedUser.getUsername());
        } else {
            contents = ADDUSER(addingUser.getUsername(), addedUser.getUsername());
        }

        Activity activity = new Activity(contents, card, addingUser);

        activityRepository.save(activity);
    }

    public void makeActivityByDeleteUser(Long cardId, Long userId, UserDetailsImpl userDetails) {
        Card card = cardRepository.findById(cardId).orElseThrow(
                () -> new IllegalArgumentException("aop, search by cardId. 없을리가 없습니다."));
        User deletedUser = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("aop, search by userId. 없을리가 없습니다."));
        User deletingUser = userDetails.getUser();

        String contents = "";
        if (deletingUser.equals(deletedUser)) {
            contents = DELETEUSERSELF(deletedUser.getUsername());
        } else {
            contents = DELETEUSER(deletingUser.getUsername(), deletedUser.getUsername());
        }

        Activity activity = new Activity(contents, card, deletingUser);

        activityRepository.save(activity);
    }

    public String postComment(CommentRequestDto requestDto, Long boardId, Long columnId, Long cardId, UserDetailsImpl userDetails) {
        boardRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 board입니다."));
        columnRepository.findById(columnId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 column입니다."));
        Card card = cardRepository.findById(cardId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 card입니다."));
        User user = userDetails.getUser();

        Activity activity = new Activity(requestDto, card, user);
        activityRepository.save(activity);

        return "comment가 추가되었습니다.";
    }
}
