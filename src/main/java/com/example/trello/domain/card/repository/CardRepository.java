package com.example.trello.domain.card.repository;

import com.example.trello.domain.card.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {

    List<Card> findAllByColumns_Id(Long columnId);
}
