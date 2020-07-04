package com.mycard.cards.service;

import com.mycard.cards.dto.CardDTO;
import com.mycard.cards.dto.PostCardDTO;
import com.mycard.cards.entity.Card;
import com.mycard.cards.entity.id.CardId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CardService {
    Card saveCard(Card card);

    Optional<Card> updateCard(Card card);

    List<Card> getUserCardList(Long userId);

    Optional<Card> getUserCard(CardId id, Long userId);

    List<CardDTO> getUserCardDTOList(Long userId);

    Optional<CardDTO> getUserCardDTO(CardId cardId, Long userId);

    CardDTO saveCardDTO(PostCardDTO postCardDTO);

    Optional<CardDTO> updateCardDTO(CardDTO cardDTO);

    Page<CardDTO> getCardDTOPage(Pageable pageable);
}
