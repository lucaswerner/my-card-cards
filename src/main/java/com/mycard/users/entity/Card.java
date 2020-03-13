package com.mycard.users.entity;

import com.mycard.users.entity.id.CardId;
import com.mycard.users.enumeration.CardFeature;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "card")
public @Data
class Card {

    @EmbeddedId
    private CardId compositeId;

    @Column(nullable = false, name = "expiration")
    private LocalDate expiration;

    @Column(nullable = false, name = "valid_from")
    private LocalDateTime validFrom;

    @Column(nullable = false, name = "feature")
    private CardFeature feature;

    @ManyToOne
    @MapsId("bin")
    @JoinColumn(name = "bin", referencedColumnName = "bin")
    private CardClass cardClass;

}
