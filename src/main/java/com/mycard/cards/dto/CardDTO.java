package com.mycard.cards.dto;

import com.mycard.cards.enumeration.CardFeature;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public @Data
class CardDTO implements Serializable {
    private static final long serialVersionUID = -8765111780619787072L;

    private Long bin;
    private Long number;
    private LocalDate expiration;
    private LocalDateTime validFrom;
    private CardFeature feature;
    private Long userId;
}
