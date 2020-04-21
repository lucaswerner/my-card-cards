package com.mycard.cards.entity;

import com.mycard.cards.entity.id.CardId;
import com.mycard.cards.enumeration.CardFeature;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "card")
public @Data
class Card {

    @EmbeddedId
    private CardId cardId;

    @Column(nullable = false, name = "expiration")
    private LocalDate expiration;

    @Column(nullable = false, name = "valid_from")
    private LocalDateTime validFrom;

    @Column(nullable = false, name = "feature")
    private CardFeature feature;

    @Column(nullable = false, name = "userId")
    private Long userId;

    @ManyToOne
    @MapsId("bin")
    @JoinColumn(name = "bin", referencedColumnName = "bin")
    private CardClass cardClass;

}
