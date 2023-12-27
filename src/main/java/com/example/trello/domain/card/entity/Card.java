package com.example.trello.domain.card.entity;

import com.example.trello.domain.card.dto.CardRequestDto;
import com.example.trello.domain.card.dto.CardTitleUpdateRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor

@Entity
public class Card {

    // 컬럼 - 연관관계 컬럼을 제외한 컬럼을 정의합니다.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    private String description;
    private String coverImg;
    private LocalDateTime dueDate;
    private Boolean isArchived;

    // 생성자 - 약속된 형태로만 생성가능하도록 합니다.
    public Card(CardRequestDto requestDto, Column column) {
        this.title = requestDto.getTitle();
        this.description = requestDto.getDescription();
        this.coverImg = requestDto.getCoverImg();
        this.dueDate = requestDto.getDueDate();
        this.isArchived = requestDto.getIsArchived();
        this.column = column;
    }

    // 연관관계 - Foreign Key 값을 따로 컬럼으로 정의하지 않고 연관 관계로 정의합니다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "column_id")
    private Column column;

    // 연관관계 편의 메소드 - 반대쪽에는 연관관계 편의 메소드가 없도록 주의합니다.


    // 서비스 메소드 - 외부에서 엔티티를 수정할 메소드를 정의합니다. (단일 책임을 가지도록 주의합니다.)
    public void updateCardTitle(CardTitleUpdateRequestDto requestDto) {
        this.title = requestDto.getTitle();
    }





}