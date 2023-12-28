package com.example.trello.domain.card.repository;

import com.example.trello.domain.board.entity.Board;
import com.example.trello.domain.card.entity.Card;
import com.example.trello.domain.column.entity.Columns;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Arrays;
import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {

    List<Card> findAllByColumns_Id(Long columnId);

    @Query("SELECT MAX(c.cardOrder) FROM Card c WHERE c.columns = :columns")
    Integer findMaxColumnOrderByColumns(@Param("columns")Columns columns);

    List<Card> findAllByColumnsAndCardOrderGreaterThanAndCardOrderLessThanEqual(Columns column, Integer cardOrder, Integer cardOrder1);

    List<Card> findAllByColumnsAndCardOrderLessThanAndCardOrderGreaterThanEqual(Columns column, Integer cardOrder, Integer cardOrder1);
}
