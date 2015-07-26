package com.galiamov.bet.repository;

import com.galiamov.bet.model.Game;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GameRepository extends MongoRepository<Game, String> {

    Game findByName(String name);

}
