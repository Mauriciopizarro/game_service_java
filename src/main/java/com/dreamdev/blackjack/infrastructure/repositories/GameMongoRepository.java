package com.dreamdev.blackjack.infrastructure.repositories;

import com.dreamdev.blackjack.domain.Game;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameMongoRepository extends MongoRepository<Game, String> {

}
