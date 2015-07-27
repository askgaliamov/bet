package com.galiamov.bet.simulator;

import com.galiamov.bet.model.Game;
import com.galiamov.bet.repository.GameRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static java.lang.System.currentTimeMillis;
import static java.util.concurrent.TimeUnit.SECONDS;

@Component
public class SimulatorComponent {

    private static final Logger LOG = LoggerFactory.getLogger(SimulatorComponent.class);

    private static final String[] gamesNames
            = new String[] { "Latvia - Spain", "France - Germany", "China - Portugal", "Poland - India" };

    private static final String[] types = new String[] { "win", "draw", "lose" };

    private static final Random random = new Random();

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private SimpMessagingTemplate template;

    @Value("${demo}")
    private Boolean demo;

    public SimulatorComponent() {
        run();
    }

    private void run() {
        scheduler.schedule(new Runnable() {
            public void run() {

                if (!Boolean.TRUE.equals(demo)) {
                    return;
                }

                LOG.info("Run demo");

                gameRepository.deleteAll();

                insertGames();

                while (true) {
                    try {
                        Thread.sleep(getRandomInterval());
                        String name = gamesNames[random.nextInt(gamesNames.length)];
                        String type = types[(random.nextInt(types.length))];

                        updateGame(name, type);

                    } catch (Exception ignore) {
                    }
                }
            }
        }, 15, SECONDS);
    }

    private void updateGame(String name, String type) {
        GameUpdate gameUpdate = new GameUpdate(name, type, getRandomOdd());
        template.convertAndSend("/topic/new_bets", gameUpdate);

        Game game = gameRepository.findByName(name);
        game.updateOdd(gameUpdate.getOdd());
        gameRepository.save(game);
    }

    private void insertGames() {
        for (String name : gamesNames) {
            Map<String, Object> odd = new HashMap<String, Object>(4);
            odd.put("name", "1x2");
            for (String type : types) {
                odd.put(type, getRandomOdd());
            }
            Game game = new Game(name, currentTimeMillis(), odd);
            gameRepository.insert(game);
        }
    }

    private int getRandomInterval() {
        return 100 + random.nextInt(400);
    }

    private double getRandomOdd() {
        double rangeMin = 0.1;
        double rangeMax = 20.9;
        double odd = rangeMin + (rangeMax - rangeMin) * random.nextDouble();
        return new BigDecimal(odd).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    static class GameUpdate {

        private String name;
        private Map<String, Double> odd;

        public GameUpdate(String name, String type, Double odd) {
            this.name = name;
            Map<String, Double> oddMap = new HashMap<String, Double>(1);
            oddMap.put(type, odd);
            this.odd = oddMap;
        }

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
