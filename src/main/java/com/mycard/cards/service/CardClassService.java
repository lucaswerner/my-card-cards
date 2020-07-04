package com.mycard.cards.service;

import com.mycard.cards.dto.CardClassDTO;
import com.mycard.cards.dto.PostCardClassDTO;
import com.mycard.cards.entity.CardClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CardClassService {
    Optional<CardClass> getCardClass(Long id);

    Page<CardClass> getCardClassPage(Pageable pageable);

    CardClass saveCardClass(CardClass cardClass);

    Optional<CardClass> updateCardClass(CardClass cardClass);

    Page<CardClassDTO> getCardClassDTOPage(Pageable pageable);

    Optional<CardClassDTO> getCardClassDTO(Long bin);

    CardClassDTO saveCardClassDTO(PostCardClassDTO postCardClassDTO);

    Optional<CardClassDTO> updateCardClassDTO(CardClassDTO cardClassDTO);
}
