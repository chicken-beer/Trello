package com.example.trello.domain.card.service;

import com.example.trello.domain.card.dto.CardRequestDto;
import com.example.trello.domain.card.dto.CardResponseDto;
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
        Column column = findBoardAndColumnByIds(boardId, columnId);

        Card card = new Card(requestDto, column);
        cardRepository.save(card);

        return new CommonResponseDto("카드 생성 성공", HttpStatus.CREATED.value());
    }

    public CardResponseDto getCard(Long boardId, Long columnId, Long cardId) {
        Column column = findBoardAndColumnByIds(boardId, columnId);

        Card card = cardRepository.findById(columnId).orElseThrow(() ->
                new NoSuchElementException("해당 카드를 찾을 수 없습니다. ID: " + cardId));

        return new CardResponseDto(card);
    }

    @Transactional
    public CommonResponseDto updateCard(Long boardId, Long columnId, Long cardId, CardRequestDto requestDto, User user) {
        Column column = findBoardAndColumnByIds(boardId, columnId);

        Card card = cardRepository.findById(columnId).orElseThrow(() ->
                new NoSuchElementException("해당 카드를 찾을 수 없습니다. ID: " + cardId));

        card.updateCard(requestDto);
        return new CommonResponseDto("카드 수정 완료", HttpStatus.CREATED.value());
    }

    public CommonResponseDto deleteCard(Long boardId, Long columnId, Long cardId, User user) {
        Column column = findBoardAndColumnByIds(boardId, columnId);

        Card card = cardRepository.findById(columnId).orElseThrow(() ->
                new NoSuchElementException("해당 카드를 찾을 수 없습니다. ID: " + cardId));

        cardRepository.delete(card);
        return new CommonResponseDto("카드 삭제 완료", HttpStatus.NO_CONTENT.value());
    }

    private Column findBoardAndColumnByIds(Long boardId, Long columnId) {

        Board board = boardRepository.findById(boardId).orElseThrow(() ->
                new NoSuchElementException("해당 보드를 찾을 수 없습니다. ID: " + boardId));

        Column column = columnRepository.findById(columnId).orElseThrow(() ->
                new NoSuchElementException("해당 컬럼을 찾을 수 없습니다. ID: " + columnId));

        return column;
    }
}
