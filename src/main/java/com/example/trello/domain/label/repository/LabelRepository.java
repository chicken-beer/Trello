package com.example.trello.domain.label.repository;

import com.example.trello.domain.label.entity.Label;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LabelRepository extends JpaRepository< Label, Long > {
}
