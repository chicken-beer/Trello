package com.example.trello.domain.column.entity;

import com.example.trello.domain.board.entity.Board;
import com.example.trello.domain.column.dto.ColumnRequestDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Columns {

    @Id
    @Column(name = "column_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "컬럼명을 반드시 입력하세요")
    private String name;

    private Boolean isArchived;

    private Integer columnOrder;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    public Columns(Board board, ColumnRequestDto requestDto, Integer columnOrder) {
        this.board = board;
        this.name = requestDto.getName();
        isArchived=false;
        this.columnOrder = columnOrder+1;
    }

    public void updateName(ColumnRequestDto requestDto) {
        this.name = requestDto.getName();
    }

    public void delete() {
        this.isArchived = true;
    }

    public void updateColumnOder(Integer column2ColumnOrder) {
        this.columnOrder = column2ColumnOrder;
    }
}