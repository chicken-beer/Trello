package com.example.trello.domain.column.repository;

import com.example.trello.domain.board.Board;
import com.example.trello.domain.column.entity.Columns;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ColumnRepository extends JpaRepository<Columns,Long> {

    List<Columns> findAllByBoardOrderByColumnOrder(Board board);

    Optional<Columns> findTopByOrderByColumnOrderDesc();
}
