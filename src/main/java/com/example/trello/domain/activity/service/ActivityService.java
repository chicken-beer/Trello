package com.example.trello.domain.activity.service;

import com.example.trello.domain.activity.dto.ActivityResponseDto;
import com.example.trello.domain.activity.dto.CommentRequestDto;
import com.example.trello.domain.activity.entity.Activity;
import com.example.trello.domain.activity.repository.ActivityRepository;
import com.example.trello.domain.board.entity.Board;
import com.example.trello.domain.board.repository.BoardRepository;
import com.example.trello.domain.card.entity.Card;
import com.example.trello.domain.card.repository.CardRepository;
import com.example.trello.domain.column.entity.Columns;
import com.example.trello.domain.column.repository.ColumnRepository;
import com.example.trello.domain.user.entity.User;
import com.example.trello.domain.user.repository.UserRepository;
import com.example.trello.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityService extends ActivityContents {

    private final ActivityRepository activityRepository;
    private final UserRepository userRepository;
    private final CardRepository cardRepository;
    private final BoardRepository boardRepository;
    private final ColumnRepository columnRepository;


    public void makeActivityByAddUser(Long cardId, Long userId, UserDetailsImpl userDetails) {
        Card card = checkCardById(cardId);

        User addedUser = checkUserId(userId);
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
        Card card = checkCardById(cardId);

        User deletedUser = checkUserId(userId);
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
        checkBoardById(boardId);
        checkColumnsById(columnId);
        Card card = checkCardById(cardId);
        User user = userDetails.getUser();

        Activity activity = new Activity(requestDto, card, user);
        activityRepository.save(activity);

        return "comment가 추가되었습니다.";
    }

    @Transactional
    public String updateComment(CommentRequestDto requestDto, Long boardId, Long columnId, Long cardId, Long activityId, UserDetailsImpl userDetails) {
        checkBoardById(boardId);
        checkColumnsById(columnId);
        checkCardById(cardId);
        Activity activity = checkActivityId(activityId);

        User user = userDetails.getUser();
        if (!user.getUsername().equals(activity.getUser().getUsername())) {
            throw new IllegalArgumentException("작성자만 수정 할 수 있습니다.");
        }

        activity.update(requestDto);

        return "comment가 수정되었습니다.";
    }

    public String deleteComment(Long boardId, Long columnId, Long cardId, Long activityId, UserDetailsImpl userDetails) {
        checkBoardById(boardId);
        checkColumnsById(columnId);
        checkCardById(cardId);
        Activity activity = checkActivityId(activityId);

        User user = userDetails.getUser();
        if (!user.getUsername().equals(activity.getUser().getUsername())) {
            throw new IllegalArgumentException("작성자만 삭제 할 수 있습니다.");
        }

        activityRepository.delete(activity);
        return "comment가 삭제되었습니다.";
    }

    public List<ActivityResponseDto> getActivitiesFromCard(Long boardId, Long columnId, Long cardId) {
        checkBoardById(boardId);
        checkColumnsById(columnId);
        Card card = checkCardById(cardId);

        return activityRepository.findAllByCard(card).stream().map(ActivityResponseDto::new).toList();
    }

    private Board checkBoardById(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 board입니다."));
    }

    private Columns checkColumnsById(Long columnsId) {
        return columnRepository.findById(columnsId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 column입니다."));
    }

    private Card checkCardById(Long cardId) {
        return cardRepository.findById(cardId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 card입니다."));
    }

    private User checkUserId(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("aop, search by userId. 없을리가 없습니다."));
    }

    private Activity checkActivityId(Long activityId) {
        return activityRepository.findById(activityId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 activity입니다."));
    }
}
