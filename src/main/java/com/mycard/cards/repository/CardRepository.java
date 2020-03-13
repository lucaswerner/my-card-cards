package com.mycard.cards.repository;

import com.mycard.cards.entity.Card;
import com.mycard.cards.entity.id.CardId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, CardId> {
}
