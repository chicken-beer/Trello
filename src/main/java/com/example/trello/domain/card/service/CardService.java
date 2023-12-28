package com.example.trello.domain.card.service;

import com.example.trello.domain.board.entity.Board;
import com.example.trello.domain.board.repository.BoardRepository;
import com.example.trello.domain.card.dto.CardDueDateUpdateRequestDto;
import com.example.trello.domain.card.dto.CardRequestDto;
import com.example.trello.domain.card.dto.CardResponseDto;
import com.example.trello.domain.card.dto.CardTitleUpdateRequestDto;
import com.example.trello.domain.card.entity.Card;
import com.example.trello.domain.card.repository.CardRepository;
import com.example.trello.domain.column.entity.Columns;
import com.example.trello.domain.column.repository.ColumnRepository;
import com.example.trello.domain.user.entity.User;
import com.example.trello.domain.user.repository.UserRepository;
import com.example.trello.global.response.CommonResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@RequiredArgsConstructor

@Service
public class CardService {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final ColumnRepository columnRepository;
    private final CardRepository cardRepository;

    public CommonResponseDto postCard(Long boardId, Long columnId, CardRequestDto requestDto, User user) {
        Board board = boardRepository.findById(boardId).orElseThrow(() ->
                new NoSuchElementException("해당 보드를 찾을 수 없습니다. ID: " + boardId));
        Columns columns = columnRepository.findById(columnId).orElseThrow(() ->
                new NoSuchElementException("해당 컬럼을 찾을 수 없습니다. ID: " + columnId));

        Card card = new Card(requestDto, columns);
        cardRepository.save(card);

        return new CommonResponseDto("카드 생성 성공", HttpStatus.CREATED.value());
    }

    public CardResponseDto getCard(Long boardId, Long columnId, Long cardId) {
        findBoardAndColumnByIds(boardId, columnId);
        Card card = cardRepository.findById(cardId).orElseThrow(() ->
                new NoSuchElementException("해당 카드를 찾을 수 없습니다. ID: " + cardId));

        return new CardResponseDto(card);
    }

    @Transactional
    public CommonResponseDto updateCardTitle(Long boardId, Long columnId, Long cardId, CardTitleUpdateRequestDto requestDto, User user) {
        findBoardAndColumnByIds(boardId, columnId);
        Card card = cardRepository.findById(cardId).orElseThrow(() ->
                new NoSuchElementException("해당 카드를 찾을 수 없습니다. ID: " + cardId));

        card.updateCardTitle(requestDto);

        return new CommonResponseDto("카드 제목 수정 성공", HttpStatus.OK.value());
    }

    @Transactional
    public ResponseEntity<CommonResponseDto> updateDueDate(Long boardId, Long columnId, Long cardId, CardDueDateUpdateRequestDto requestDto, User user) {
        findBoardAndColumnByIds(boardId, columnId);
        Card card = cardRepository.findById(cardId).orElseThrow(() ->
                new NoSuchElementException("해당 카드를 찾을 수 없습니다. ID: " + cardId));

        card.setDueDate(requestDto.getDueDate());

        if(card.getDueDate() == null) {
            return ResponseEntity.status(HttpStatus.OK).body(new CommonResponseDto("종료 기한 삭제", HttpStatus.OK.value()));
        }

        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponseDto("종료 기한 수정", HttpStatus.OK.value()));
    }

    @Transactional
    public CommonResponseDto toggleIsArchived(Long boardId, Long columnId, Long cardId, User user) {
        findBoardAndColumnByIds(boardId, columnId);
        Card card = cardRepository.findById(cardId).orElseThrow(() ->
                new NoSuchElementException("해당 카드를 찾을 수 없습니다. ID: " + cardId));

        if(card.getIsArchived()) {
            card.setIsArchived(false);
            return new CommonResponseDto("카드 숨김 취소", HttpStatus.OK.value());
        } else {
            card.setIsArchived(true);
            return new CommonResponseDto("카드 숨김", HttpStatus.OK.value());
        }
    }

    public CommonResponseDto deleteCard(Long boardId, Long columnId, Long cardId, User user) {
        findBoardAndColumnByIds(boardId, columnId);
        Card card = cardRepository.findById(cardId).orElseThrow(() ->
                new NoSuchElementException("해당 카드를 찾을 수 없습니다. ID: " + cardId));

        cardRepository.delete(card);

        return new CommonResponseDto("카드 삭제 완료 ", HttpStatus.NO_CONTENT.value());
    }

    @Transactional
    public ResponseEntity<CommonResponseDto> toggleUserToCard(Long boardId, Long columnId, Long cardId, Long userId, User user1) {
        findBoardAndColumnByIds(boardId, columnId);
        Card card = cardRepository.findById(cardId).orElseThrow(() ->
                new NoSuchElementException("해당 카드를 찾을 수 없습니다. ID: " + cardId));
        User user = userRepository.findById(userId).orElseThrow(() ->
                new NoSuchElementException("해당 유저를 찾을 수 없습니다. ID: " + userId));

        if (card.getUsers().contains(user)) {
            card.getUsers().remove(user);
            return ResponseEntity.status(HttpStatus.OK).body(new CommonResponseDto("카드 유저 제거", HttpStatus.OK.value()));
        } else {
            card.getUsers().add(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(new CommonResponseDto("카드 유저 등록", HttpStatus.CREATED.value()));
        }
    }

    private void findBoardAndColumnByIds(Long boardId, Long columnId) {
        boardRepository.findById(boardId).orElseThrow(() ->
            new NoSuchElementException("해당 보드를 찾을 수 없습니다. ID: " + boardId));
        columnRepository.findById(columnId).orElseThrow(() ->
            new NoSuchElementException("해당 컬럼을 찾을 수 없습니다. ID: " + columnId));
    }
}
