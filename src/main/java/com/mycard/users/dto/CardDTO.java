package com.mycard.users.dto;

import com.mycard.users.enumeration.CardFeature;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

public @Data
class CardDTO {
    private Long bin;
    private Long number;
    private LocalDate expiration;
    private LocalDateTime validFrom;
    private CardFeature feature;
}