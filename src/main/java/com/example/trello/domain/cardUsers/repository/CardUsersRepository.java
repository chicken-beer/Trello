package com.example.trello.domain.cardUsers.repository;

import com.example.trello.domain.cardUsers.entity.CardUsers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardUsersRepository extends JpaRepository<CardUsers, Long> {
}
