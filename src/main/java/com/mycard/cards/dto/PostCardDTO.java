package com.mycard.cards.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

public @Data
class PostCardDTO {
    @NotNull
    private Long number;
    @NotNull
    private Long bin;
    private LocalDate expiration;
    private LocalDateTime validFrom;
    private Byte billDay;
    private Long userId;
}
