package com.mycard.cards.entity;

import com.mycard.cards.entity.id.CardId;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@IdClass(CardId.class)
@Table(name = "card")
public class Card implements Serializable {

    private static final long serialVersionUID = -3106559627256167600L;

    @Id
    @Column(nullable = false, name = "bin")
    private Long bin;

    @Id
    @Column(nullable = false, name = "number")
    private Long number;

    @Column(nullable = false, name = "expiration")
    private LocalDate expiration;

    @Column(nullable = false, name = "valid_from")
    private LocalDateTime validFrom;

    @Column(nullable = false, name = "userId")
    private Long userId;

    @Column(
            name = "bill_day",
            columnDefinition = "smallint",
            nullable = false
    )
    private Byte billDay;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("bin")
    @JoinColumn(name = "bin", referencedColumnName = "bin")
    private CardClass cardClass;

}
