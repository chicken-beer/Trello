package com.example.trello.domain.column.service;

import com.example.trello.domain.board.repository.BoardRepository;
import com.example.trello.domain.card.dto.CardResponseDto;
import com.example.trello.domain.card.repository.CardRepository;
import com.example.trello.domain.column.dto.ColumnResponseDto;
import com.example.trello.domain.column.dto.PostColumnRequestDto;
import com.example.trello.domain.column.dto.UpdateColumnRequestDto;
import com.example.trello.domain.column.entity.Columns;
import com.example.trello.domain.column.repository.ColumnRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ColumnService {

    private final ColumnRepository columnRepository;
    private final BoardRepository boardRepository;
    private final CardRepository cardRepository;

    public void postColumn(Long boardId, PostColumnRequestDto requestDto) {
        checkBoardId(boardId);

        Columns columns = new Columns(requestDto);
        columnRepository.save(columns);
    }

    @Transactional
    public void updateColumn(Long boardId, Long columnId, UpdateColumnRequestDto requestDto) {
        checkBoardId(boardId);
        Columns columns = checkColumnIdAndReturnColumn(columnId);

        columns.update(requestDto);
    }


    public void deleteColumn(Long boardId, Long columnId) {
        checkBoardId(boardId);
        Columns columns = checkColumnIdAndReturnColumn(columnId);

        columnRepository.delete(columns);
    }

    public ColumnResponseDto getColumn(Long boardId, Long columnId) {
        checkBoardId(boardId);
        Columns columns = checkColumnIdAndReturnColumn(columnId);

        List<CardResponseDto> cardResponseDtoList =
                cardRepository.findAllByColumns_Id(columnId).stream().map(CardResponseDto::new).toList();

        return new ColumnResponseDto(columns.getName(), cardResponseDtoList);
    }


    private void checkBoardId(Long boardId) {
        boardRepository.findById(boardId).orElseThrow(()
                -> new IllegalArgumentException("존재하지 않는 boardId입니다."));
    }

    private Columns checkColumnIdAndReturnColumn(Long columnId) {
        Columns columns = columnRepository.findById(columnId).orElseThrow(()
                -> new IllegalArgumentException("존재하지 않는 컬럼입니다."));
        return columns;
    }
}
