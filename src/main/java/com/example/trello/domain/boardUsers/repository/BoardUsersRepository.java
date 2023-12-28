package com.example.trello.domain.boardUsers.repository;

import com.example.trello.domain.boardUsers.entity.BoardUsers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardUsersRepository extends JpaRepository<BoardUsers, Long> {
    List<BoardUsers> findAllByUserId(Long id);

    BoardUsers findByBoardIdAndUserId(Long boardId, Long id);

    Optional<BoardUsers> findByUserId(Long id);
}
