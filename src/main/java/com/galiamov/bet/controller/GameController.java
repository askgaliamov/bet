package com.galiamov.bet.controller;

import com.galiamov.bet.model.Game;
import com.galiamov.bet.repository.GameRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class GameController {

    private static final Logger LOG = LoggerFactory.getLogger(GameController.class);

    @Autowired
    private GameRepository gameRepository;

    @RequestMapping(value = "/games", method = RequestMethod.GET)
    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    @RequestMapping(value = "/games/{name}", method = RequestMethod.GET)
    public Game getGame(@PathVariable String name) {
        return gameRepository.findByName(name);
    }

    @RequestMapping(value = "/games/update", method = RequestMethod.POST)
    @MessageMapping("/update")
    @SendTo("/topic/new_bets")
    public GameUpdate greeting(@RequestBody GameUpdate update) throws Exception {
        Game game = gameRepository.findByName(update.getName());
        game.updateOdd(update.getOdd());
        gameRepository.save(game);
        return update;
    }

    static class GameUpdate {

        String name;
        Map<String, Double> odd;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Map<String, Double> getOdd() {
            return odd;
        }

        public void setOdd(Map<String, Double> odd) {
            this.odd = odd;
        }
    }

}
