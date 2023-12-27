package com.example.trello.domain.column.service;

import com.example.trello.domain.board.Board;
import com.example.trello.domain.board.BoardRepository;
import com.example.trello.domain.card.dto.CardResponseDto;
import com.example.trello.domain.card.repository.CardRepository;
import com.example.trello.domain.column.dto.ColumnRequestDto;
import com.example.trello.domain.column.dto.ColumnResponseDto;
import com.example.trello.domain.column.entity.Columns;
import com.example.trello.domain.column.repository.ColumnRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ColumnService {

    private final ColumnRepository columnRepository;
    private final BoardRepository boardRepository;
    private final CardRepository cardRepository;

    public void postColumn(Long boardId, ColumnRequestDto requestDto) {
        Board board = checkBoardId(boardId);

        Columns columns = new Columns(board,requestDto);
        columnRepository.save(columns);
    }

    @Transactional
    public void updateColumn(Long boardId, Long columnId, ColumnRequestDto requestDto) {
        checkBoardId(boardId);
        Columns columns = checkColumnId(columnId);

        columns.update(requestDto);
    }

    @Transactional
    public void deleteColumn(Long boardId, Long columnId) {
        checkBoardId(boardId);
        Columns columns = checkColumnId(columnId);

        columns.delete();
    }

    public ColumnResponseDto getColumn(Long boardId, Long columnId) {
        checkBoardId(boardId);
        Columns columns = checkColumnId(columnId);

        List<CardResponseDto> cardResponseDtoList =
                cardRepository.findAllByColumns_Id(columnId).stream().map(CardResponseDto::new).toList();

        return new ColumnResponseDto(columns.getName(), cardResponseDtoList);
    }

    public List<ColumnResponseDto> getColumnList(Long boardId) {
        Board board = checkBoardId(boardId);

        List<ColumnResponseDto> columnResponseDtoList = new ArrayList<>();
        columnRepository.findAllByBoard(board).stream().forEach(a -> {
            columnResponseDtoList.add(new ColumnResponseDto(
                    a.getName(),
                    cardRepository.findAllByColumns_Id(a.getId()).stream().map(CardResponseDto::new).toList()
                    ));
        });

        return columnResponseDtoList;
    }


    private Board checkBoardId(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(()
                -> new IllegalArgumentException("존재하지 않는 boardId입니다."));
        return board;
    }

    private Columns checkColumnId(Long columnId) {
        Columns columns = columnRepository.findById(columnId).orElseThrow(()
                -> new IllegalArgumentException("존재하지 않는 컬럼입니다."));
        return columns;
    }
}
