package com.mycard.cards.repository;

import com.mycard.cards.entity.Card;
import com.mycard.cards.entity.id.CardId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, CardId> {

    List<Card> findAllByUserId(Long userId, Pageable pageable);

    Optional<Card> findByCardIdAndUserId(CardId id, Long userId);
}
