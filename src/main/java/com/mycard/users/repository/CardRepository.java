package com.mycard.users.repository;

import com.mycard.users.entity.Card;
import com.mycard.users.entity.id.CardId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, CardId> {
}
