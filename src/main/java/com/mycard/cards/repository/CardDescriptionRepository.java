package com.mycard.cards.repository;

import com.mycard.cards.entity.CardDescription;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardDescriptionRepository extends CrudRepository<CardDescription, String> {
}
