package com.mycard.cards.repository;

import com.mycard.cards.entity.Card;
import com.mycard.cards.entity.id.CardId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardRepository extends CrudRepository<Card, CardId> {

    List<Card> findAllByUserId(Long userId, Pageable pageable);

    Optional<Card> findByBinAndNumberAndUserId(Long bin, Long number, Long userId);
}
