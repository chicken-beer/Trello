package com.example.trello.domain.column.entity;

import com.example.trello.domain.column.dto.PostColumnRequestDto;
import com.example.trello.domain.column.dto.UpdateColumnRequestDto;
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


    public Columns(PostColumnRequestDto requestDto) {
        this.name = requestDto.getName();
        isArchived=false;
    }

    public void update(UpdateColumnRequestDto requestDto) {
        this.name = requestDto.getName();
    }

    public void delete() {
        this.isArchived = true;
    }
}