package com.example.trello.domain.card.service;

import com.example.trello.domain.board.dto.BoardResponseDto;
import com.example.trello.domain.board.entity.Board;
import com.example.trello.domain.board.repository.BoardRepository;
import com.example.trello.domain.card.dto.*;
import com.example.trello.domain.card.entity.Card;
import com.example.trello.domain.card.repository.CardRepository;
import com.example.trello.domain.cardUsers.entity.CardUsers;
import com.example.trello.domain.cardUsers.repository.CardUsersRepository;
import com.example.trello.domain.column.entity.Columns;
import com.example.trello.domain.column.repository.ColumnRepository;
import com.example.trello.domain.user.entity.User;
import com.example.trello.domain.user.repository.UserRepository;
import com.example.trello.global.response.CommonResponseDto;
import com.example.trello.global.s3.S3Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.NoSuchElementException;

@RequiredArgsConstructor

@Service
public class CardService {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final ColumnRepository columnRepository;
    private final CardRepository cardRepository;
    private final CardUsersRepository cardUsersRepository;
    private final S3Utils s3Utils;

    public CommonResponseDto postCard(Long boardId, Long columnId, CardRequestDto requestDto, User user) {
        Board board = boardRepository.findById(boardId).orElseThrow(() ->
                new NoSuchElementException("해당 보드를 찾을 수 없습니다. ID: " + boardId));
        Columns columns = columnRepository.findById(columnId).orElseThrow(() ->
                new NoSuchElementException("해당 컬럼을 찾을 수 없습니다. ID: " + columnId));

        Integer lastCardOrderInColumns = cardRepository.findMaxColumnOrderByColumns(columns);
        if (lastCardOrderInColumns==null) {
            lastCardOrderInColumns=0;
        }
        Card card;
        if(requestDto.getFile() != null){
            String filename = s3Utils.uploadFile(requestDto.getFile());
            card = new Card(requestDto, columns, lastCardOrderInColumns, filename);
        } else{
            card = new Card(requestDto, columns, lastCardOrderInColumns);
        }
        cardRepository.save(card);


        return new CommonResponseDto("카드 생성 성공", HttpStatus.CREATED.value());
    }

    public CardResponseDto getCard(Long boardId, Long columnId, Long cardId) {
        findBoardAndColumnByIds(boardId, columnId);
        Card card = cardRepository.findById(cardId).orElseThrow(() ->
                new NoSuchElementException("해당 카드를 찾을 수 없습니다. ID: " + cardId));

        if(card.getFilename() != null){
            String filename = card.getFilename();
            String imageURL = s3Utils.getFileURL(filename);

            return new CardResponseDto(card,imageURL);
        } else{
            return new CardResponseDto(card);
        }
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
    public CommonResponseDto updateFile(Long boardId, Long columnId, Long cardId, CardFileRequestDto cardFileRequestDto){
        findBoardAndColumnByIds(boardId, columnId);
        Card card = cardRepository.findById(cardId).orElseThrow(() ->
                new NoSuchElementException("해당 카드를 찾을 수 없습니다. ID: " + cardId));

        if(cardFileRequestDto.getFile() == null){
            if(card.getFilename() != null) {
                String filename = card.getFilename();
                s3Utils.deleteFile(filename);
                card.updateFilename(null);
            }
        } else{
            if(card.getFilename() != null) {
                String filename = card.getFilename();
                s3Utils.deleteFile(filename);
            }

            String newFilename = s3Utils.uploadFile(cardFileRequestDto.getFile());
            card.updateFilename(newFilename);
        }

        return new CommonResponseDto("파일 수정 성공", HttpStatus.OK.value());
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

        String filename = card.getFilename();
        s3Utils.deleteFile(filename);
        cardRepository.delete(card);

        return new CommonResponseDto("카드 삭제 완료 ", HttpStatus.NO_CONTENT.value());
    }


    public String addUserToCard(Long boardId, Long columnId, Long cardId, Long userId, User addingUser) {
        findBoardAndColumnByIds(boardId, columnId);
        Card card = cardRepository.findById(cardId).orElseThrow(() ->
                new NoSuchElementException("해당 카드를 찾을 수 없습니다. ID: " + cardId));
        User addedUser = userRepository.findById(userId).orElseThrow(() ->
                new NoSuchElementException("해당 유저를 찾을 수 없습니다. ID: " + userId));

        if (cardUsersRepository.findByCardAndAddedUser(card, addedUser).isPresent()) {
            throw new IllegalArgumentException("이미 카드의 멤버입니다.");
        }

        CardUsers cardUsers = new CardUsers(card,addingUser,addedUser);
        cardUsersRepository.save(cardUsers);

        return addedUser.getUsername()+"를 카드에 추가했습니다.";
    }

    public String deleteUserFromCard(Long boardId, Long columnId, Long cardId, Long userId, User user) {
        findBoardAndColumnByIds(boardId, columnId);
        Card card = cardRepository.findById(cardId).orElseThrow(() ->
                new NoSuchElementException("해당 카드를 찾을 수 없습니다. ID: " + cardId));
        User addedUser = userRepository.findById(userId).orElseThrow(() ->
                new NoSuchElementException("해당 유저를 찾을 수 없습니다. ID: " + userId));

        CardUsers cardUsers = cardUsersRepository.findByCardAndAddedUser(card,addedUser)
                .orElseThrow(()-> new IllegalArgumentException("카드의 멤버가 아닙니다."));

        cardUsersRepository.delete(cardUsers);

        return addedUser.getUsername()+"가 카드에서 삭제되었습니다";
    }

    @Transactional
    public void updateCardOrder(Long boardId, Long columnId, Long cardId, Integer cardOrder) {
        boardRepository.findById(boardId).orElseThrow(() ->
                new NoSuchElementException("해당 보드를 찾을 수 없습니다. ID: " + boardId));
        Columns column = columnRepository.findById(columnId).orElseThrow(() ->
                new NoSuchElementException("해당 컬럼을 찾을 수 없습니다. ID: " + columnId));
        Card card = cardRepository.findById(cardId).orElseThrow(() ->
                new NoSuchElementException("해당 카드를 찾을 수 없습니다. ID: " + cardId));

        Integer lastCardOrderInColumns = cardRepository.findMaxColumnOrderByColumns(column);
        if (cardOrder > lastCardOrderInColumns) {
            throw new IllegalArgumentException("입력한 순서가 카드의 개수를 초과합니다.");
        }
        if (cardOrder < 1) {
            throw new IllegalArgumentException("컬럼 순서는 1부터 시작합니다.");
        }
        if (card.getCardOrder()==cardOrder) {
            throw new IllegalArgumentException("기존과 동일한 카드 순서입니다.");
        }

        if (card.getCardOrder() < cardOrder) {
            cardRepository.findAllByColumnsAndCardOrderGreaterThanAndCardOrderLessThanEqual(
                    column, card.getCardOrder(), cardOrder)
                    .stream().forEach(a -> a.updateCardOrder(a.getCardOrder()-1));
            card.updateCardOrder(cardOrder);
        }

        if (card.getCardOrder() > cardOrder) {
            cardRepository.findAllByColumnsAndCardOrderLessThanAndCardOrderGreaterThanEqual(
                            column, card.getCardOrder(), cardOrder)
                    .stream().forEach(a -> a.updateCardOrder(a.getCardOrder()+1));
            card.updateCardOrder(cardOrder);
        }
    }

    @Transactional
    public void moveCardToAnotherColumns(Long boardId, Long columnId, Long cardId) {
        boardRepository.findById(boardId).orElseThrow(() ->
                new NoSuchElementException("해당 보드를 찾을 수 없습니다. ID: " + boardId));
        Columns columns = columnRepository.findById(columnId).orElseThrow(() ->
                new NoSuchElementException("해당 컬럼을 찾을 수 없습니다. ID: " + columnId));
        Card card = cardRepository.findById(cardId).orElseThrow(() ->
                new NoSuchElementException("해당 카드를 찾을 수 없습니다. ID: " + cardId));

        if (card.getColumns().getId().equals(columns.getId())) {
            throw new IllegalArgumentException("동일한 컬럼으로 이동할 수 없습니다.");
        }

        Integer lastCardOrderInColumns = cardRepository.findMaxColumnOrderByColumns(columns);
        if (lastCardOrderInColumns==null) {
            lastCardOrderInColumns=0;
        }

        card.updateColumns(columns,lastCardOrderInColumns);
    }


    private void findBoardAndColumnByIds(Long boardId, Long columnId) {
        boardRepository.findById(boardId).orElseThrow(() ->
            new NoSuchElementException("해당 보드를 찾을 수 없습니다. ID: " + boardId));
        columnRepository.findById(columnId).orElseThrow(() ->
            new NoSuchElementException("해당 컬럼을 찾을 수 없습니다. ID: " + columnId));
    }
}
