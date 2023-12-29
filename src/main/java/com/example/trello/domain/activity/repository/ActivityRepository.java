package com.example.trello.domain.activity.repository;

import com.example.trello.domain.activity.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<Activity,Long> {
}
