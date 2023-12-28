package com.example.trello.domain.card.entity;

import com.example.trello.domain.card.dto.CardRequestDto;
import com.example.trello.domain.card.dto.CardTitleUpdateRequestDto;
import com.example.trello.domain.column.entity.Columns;
import com.example.trello.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor

@Entity
public class Card {

    // 컬럼 - 연관관계 컬럼을 제외한 컬럼을 정의합니다.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String coverImg;
    private LocalDateTime dueDate;
    private Boolean isArchived;
    private Integer cardOrder;

    // 생성자 - 약속된 형태로만 생성가능하도록 합니다.
    public Card(CardRequestDto requestDto, Columns columns, Integer lastCardOrderInColumns) {
        this.title = requestDto.getTitle();
        this.description = requestDto.getDescription();
        this.coverImg = requestDto.getCoverImg();
        this.isArchived = requestDto.getIsArchived();
        this.columns = columns;
        this.cardOrder = lastCardOrderInColumns+1;
    }

    // 연관관계 - Foreign Key 값을 따로 컬럼으로 정의하지 않고 연관 관계로 정의합니다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "column_id")
    private Columns columns;

    @ManyToMany
    @JoinTable(name = "members",
    joinColumns = @JoinColumn(name = "card_id"),
    inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users = new ArrayList<>();

    // 연관관계 편의 메소드 - 반대쪽에는 연관관계 편의 메소드가 없도록 주의합니다.


    // 서비스 메소드 - 외부에서 엔티티를 수정할 메소드를 정의합니다. (단일 책임을 가지도록 주의합니다.)
    public void updateCardTitle(CardTitleUpdateRequestDto requestDto) {
        this.title = requestDto.getTitle();
    }

    public void updateCardOrder(Integer cardOrder) {
        this.cardOrder = cardOrder;
    }
}
