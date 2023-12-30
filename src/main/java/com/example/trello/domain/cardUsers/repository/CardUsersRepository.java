package com.example.trello.domain.cardUsers.repository;

import com.example.trello.domain.card.entity.Card;
import com.example.trello.domain.cardUsers.entity.CardUsers;
import com.example.trello.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardUsersRepository extends JpaRepository<CardUsers, Long> {

    Optional<CardUsers> findByCardAndAddedUser(Card card, User addedUser);
}
