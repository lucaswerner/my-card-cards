package com.mycard.users.repository;

import com.mycard.users.entity.CardFee;
import com.mycard.users.entity.id.CardFeeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardFeeRepository extends JpaRepository<CardFee, CardFeeId> {
}
