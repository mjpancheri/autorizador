package com.mjpancheri.authorizer.cards.domain.repository;

import com.mjpancheri.authorizer.cards.domain.Card;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {

  Optional<Card> findByNumber(String number);
}
