package com.example.trello.domain.activity.repository;

import com.example.trello.domain.activity.entity.Activity;
import com.example.trello.domain.card.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity,Long> {

    List<Activity> findAllByCard(Card card);
}
