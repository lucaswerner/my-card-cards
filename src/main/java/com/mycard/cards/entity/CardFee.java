package com.mycard.cards.entity;

import com.mycard.cards.entity.id.CardFeeId;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "card_fee")
public @Data
class CardFee {

    @EmbeddedId
    private CardFeeId compositeId;

    @Column(nullable = false, name = "value")
    private double value;

    @ManyToOne
    @MapsId("bin")
    @JoinColumn(name = "bin", referencedColumnName = "bin")
    private CardClass cardFeeClass;
}
