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

        Integer lastColumnOrderInBoard = columnRepository.findMaxColumnOrderByBoard(board);
        if (lastColumnOrderInBoard==null) {
            lastColumnOrderInBoard=0;
        }

        Columns columns = new Columns(board,requestDto, lastColumnOrderInBoard);
        columnRepository.save(columns);
    }

    @Transactional
    public void updateColumnName(Long boardId, Long columnId, ColumnRequestDto requestDto) {
        checkBoardId(boardId);
        Columns columns = checkColumnId(columnId);

        columns.updateName(requestDto);
    }

    @Transactional
    public void updateColumnOrder(Long boardId, Long columnId, Integer columnOrder) {
        Board board = checkBoardId(boardId);
        Columns targetColumn = checkColumnId(columnId);

        Integer lastColumnOrderInBoard = columnRepository.findMaxColumnOrderByBoard(board);
        if (columnOrder > lastColumnOrderInBoard) {
            throw new IllegalArgumentException("입력한 순서가 컬럼 개수를 초과합니다.");
        }
        if (columnOrder < 1) {
            throw new IllegalArgumentException("컬럼 순서는 1부터 시작합니다.");
        }

        if (targetColumn.getColumnOrder()==columnOrder) {
            throw new IllegalArgumentException("기존과 동일한 컬럼 순서 입니다.");
        }

        // 칼럼 순서를 바꿔 줄 때 순서를 앞에서 뒤로 바꾼다면
        // 그 사이에 있는 순서값들을 모두 -1 하고 순서를 옮김
        if (targetColumn.getColumnOrder()<columnOrder) {
            columnRepository.findAllByBoardAndColumnOrderGreaterThanAndColumnOrderLessThanEqual(
                    board, targetColumn.getColumnOrder(), columnOrder)
                    .stream().forEach(a -> a.updateColumnOder(a.getColumnOrder()-1));
            targetColumn.updateColumnOder(columnOrder);
        }

        // 순서를 뒤에서 앞으로 바꾼다면
        // 그 사이에 있는 순서값들을 모두 +1 하고 순서를 옮김
        if (targetColumn.getColumnOrder()>columnOrder) {
            columnRepository.findAllByBoardAndColumnOrderLessThanAndColumnOrderGreaterThanEqual(
                            board, targetColumn.getColumnOrder(), columnOrder)
                    .stream().forEach(a -> a.updateColumnOder(a.getColumnOrder()+1));
            targetColumn.updateColumnOder(columnOrder);
        }
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
        columnRepository.findAllByBoardOrderByColumnOrder(board)
                .stream().forEach(a -> {
            columnResponseDtoList.add(
                    new ColumnResponseDto(a.getName(),
                            cardRepository.findAllByColumns_Id(a.getId())
                            .stream().map(CardResponseDto::new).toList()
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
