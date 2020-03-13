package com.mycard.cards.repository;

import com.mycard.cards.entity.CardClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardClassRepository extends JpaRepository<CardClass, Long> {
}
