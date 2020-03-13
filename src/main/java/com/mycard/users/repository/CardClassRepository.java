package com.mycard.users.repository;

import com.mycard.users.entity.CardClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardClassRepository extends JpaRepository<CardClass, Long> {
}
