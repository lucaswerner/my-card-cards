package com.mycard.cards.entity;

import com.mycard.cards.entity.id.CardFeeId;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "card_fee")
public @Data
class CardFee implements Serializable {

    private static final long serialVersionUID = 7654344242351900450L;

    @EmbeddedId
    private CardFeeId compositeId;

    @Column(nullable = false, name = "value")
    private double value;

    @ManyToOne
    @MapsId("bin")
    @JoinColumn(name = "bin", referencedColumnName = "bin")
    private CardClass cardFeeClass;
}
