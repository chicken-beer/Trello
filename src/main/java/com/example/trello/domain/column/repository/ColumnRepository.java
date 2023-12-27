package com.example.trello.domain.column.repository;

import com.example.trello.domain.column.entity.Columns;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColumnRepository extends JpaRepository<Columns,Long> {
}
