package com.example.trello.domain.column.repository;

import com.example.trello.domain.board.Board;
import com.example.trello.domain.column.entity.Columns;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ColumnRepository extends JpaRepository<Columns,Long> {

    List<Columns> findAllByBoardOrderByColumnOrder(Board board);

    @Query("SELECT MAX(c.columnOrder) FROM Columns c WHERE c.board = :board")
    Integer findMaxColumnOrderByBoard(@Param("board") Board board);

    List<Columns> findAllByBoardAndColumnOrderGreaterThanAndColumnOrderLessThanEqual(
            Board board,
            Integer greaterThanColumnOrder,
            Integer lessThanOrEqualColumnOrder
    );

    List<Columns> findAllByBoardAndColumnOrderLessThanAndColumnOrderGreaterThanEqual(
            Board board,
            Integer lessThanColumnOrder,
            Integer greaterThanEqualColumnOrder);
}
