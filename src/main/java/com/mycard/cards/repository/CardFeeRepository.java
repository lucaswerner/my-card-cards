package com.mycard.cards.repository;

import com.mycard.cards.entity.CardFee;
import com.mycard.cards.entity.id.CardFeeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardFeeRepository extends JpaRepository<CardFee, CardFeeId> {
}
