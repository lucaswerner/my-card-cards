package com.mycard.cards.entity;

import com.mycard.cards.enumeration.CardBrand;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "card_class")
public @Data
class CardClass {

    @Id
    @GeneratedValue
    @Column(name = "bin")
    private Long bin;

    @Size(min = 5, max = 20)
    @Column(name = "name", nullable = false)
    private String name;

    @Column(nullable = false, name = "brand")
    private CardBrand cardBrand;

    @OneToMany(mappedBy = "cardClass", fetch = FetchType.LAZY)
    private List<Card> cardList;

    @OneToMany(mappedBy = "cardFeeClass", fetch = FetchType.LAZY)
    private List<CardFee> cardFeeList;
}
