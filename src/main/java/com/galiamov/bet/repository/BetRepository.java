package com.galiamov.bet.repository;

import com.galiamov.bet.model.Bet;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BetRepository extends MongoRepository<Bet, Integer> {

    Long countByIp(String ip);

    Long countByName(String name);

    List<Bet> findAllByName(String name);

    List<Bet> deleteAllByTimestamp(long timestamp);
}
