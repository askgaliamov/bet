package com.galiamov.bet.controller;

import com.galiamov.bet.model.Bet;
import com.galiamov.bet.repository.BetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BetReportsController {

    private static final Logger LOG = LoggerFactory.getLogger(BetReportsController.class);

    @Autowired
    private BetRepository betRepository;

    @RequestMapping(value = "/reports/count/ip", method = RequestMethod.GET)
    public CountResult countByIp(String ip) {
        return new CountResult(betRepository.countByIp(ip));
    }

    @RequestMapping(value = "/reports/count/name", method = RequestMethod.GET)
    public CountResult countByName(String name) {
        return new CountResult(betRepository.countByName(name));
    }

    @RequestMapping(value = "/reports/bets", method = RequestMethod.GET)
    public List<Bet> findAllByName(String name) {
        return betRepository.findAllByName(name);
    }

    static class CountResult {
        private long count;

        public CountResult(long count) {
            this.count = count;
        }

        public long getCount() {
            return count;
        }

        public void setCount(long count) {
            this.count = count;
        }
    }

}
