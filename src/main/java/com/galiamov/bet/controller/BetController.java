package com.galiamov.bet.controller;

import com.galiamov.bet.model.Bet;
import com.galiamov.bet.model.Game;
import com.galiamov.bet.repository.BetRepository;
import com.galiamov.bet.repository.GameRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class BetController {

    private static final Logger LOG = LoggerFactory.getLogger(BetController.class);

    @Autowired
    private BetRepository betRepository;

    @Autowired
    private GameRepository gameRepository;

    @RequestMapping(value = "/bet", method = RequestMethod.POST)
    public Boolean postBet(@RequestBody Bet bet, HttpServletRequest request) {

        bet.setTimestamp(System.currentTimeMillis());
        bet.setIp(request.getRemoteAddr());

        LOG.debug("bet: {}", bet);

        String gameName = bet.getName();
        Game game = gameRepository.findByName(gameName);

        if (game == null) {
            LOG.info("No game found, name: {}", gameName);
            return false;
        }

        LOG.debug("game: {}", game);

        String type = bet.getType();

        if (!game.isOddPresentFor(type)) {
            LOG.info("No odd for type: {}", type);
            return false;
        }

        if (!game.isOddEqualsTo(type, bet.getOdd())) {
            LOG.debug("Outdated odd");
            return false;
        }

        betRepository.save(bet);
        return true;
    }

    @RequestMapping(value = "/bet", method = RequestMethod.DELETE)
    public List<Bet> deleteItem(long timestamp) {
        return betRepository.deleteAllByTimestamp(timestamp);
    }

}