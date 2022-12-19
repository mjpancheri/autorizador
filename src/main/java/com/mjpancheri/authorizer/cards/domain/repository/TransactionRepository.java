package com.mjpancheri.authorizer.cards.domain.repository;

import com.mjpancheri.authorizer.cards.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
