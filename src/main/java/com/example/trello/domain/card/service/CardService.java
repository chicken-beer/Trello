package com.example.trello.domain.card.service;

import com.example.trello.domain.card.dto.CardRequestDto;
import com.example.trello.domain.card.dto.CardResponseDto;
import com.example.trello.domain.card.dto.CardTitleUpdateRequestDto;
import com.example.trello.domain.card.entity.Card;
import com.example.trello.domain.card.repository.CardRepository;
import com.example.trello.global.CommonResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@RequiredArgsConstructor

@Service
public class CardService {

    private final BoardRepository boardRepository;
    private final ColumnRepository columnRepository;
    private final CardRepository cardRepository;

    public CommonResponseDto postCard(Long boardId, Long columnId, CardRequestDto requestDto, User user) {
        Board board = boardRepository.findById(boardId).orElseThrow(() ->
                new NoSuchElementException("해당 보드를 찾을 수 없습니다. ID: " + boardId));
        Column column = columnRepository.findById(columnId).orElseThrow(() ->
                new NoSuchElementException("해당 컬럼을 찾을 수 없습니다. ID: " + columnId));

        Card card = new Card(requestDto, column);
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
    public CommonResponseDto toggleIsArchived(Long boardId, Long columnId, Long cardId, User user) {
        findBoardAndColumnByIds(boardId, columnId);
        Card card = cardRepository.findById(cardId).orElseThrow(() ->
                new NoSuchElementException("해당 카드를 찾을 수 없습니다. ID: " + cardId));

        if(card.getIsArchived()) {
            card.setIsArchived(false);
            return new CommonResponseDto("목표 달성 취소", HttpStatus.OK.value());
        } else {
            card.setIsArchived(true);
            return new CommonResponseDto("목표 달성", HttpStatus.OK.value());
        }
    }

    public CommonResponseDto deleteCard(Long boardId, Long columnId, Long cardId, User user) {
        findBoardAndColumnByIds(boardId, columnId);
        Card card = cardRepository.findById(cardId).orElseThrow(() ->
                new NoSuchElementException("해당 카드를 찾을 수 없습니다. ID: " + cardId));

        cardRepository.delete(card);

        return new CommonResponseDto("카드 삭제 완료", HttpStatus.NO_CONTENT.value());
    }

    private void findBoardAndColumnByIds(Long boardId, Long columnId) {
        boardRepository.findById(boardId).orElseThrow(() ->
            new NoSuchElementException("해당 보드를 찾을 수 없습니다. ID: " + boardId));
        columnRepository.findById(columnId).orElseThrow(() ->
            new NoSuchElementException("해당 컬럼을 찾을 수 없습니다. ID: " + columnId));
    }
}
