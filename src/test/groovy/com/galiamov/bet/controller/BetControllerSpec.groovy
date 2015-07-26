package com.galiamov.bet.controller

import com.galiamov.bet.model.Bet
import com.galiamov.bet.model.Game
import com.galiamov.bet.repository.BetRepository
import com.galiamov.bet.repository.GameRepository
import spock.lang.Specification

import javax.servlet.http.HttpServletRequest

class BetControllerSpec extends Specification {

    def betRepository = Mock(BetRepository)
    def gameRepository = Mock(GameRepository)

    def controller = new BetController()

    void setup() {
        controller.betRepository = betRepository
        controller.gameRepository = gameRepository
    }

    def ip = "127.0.0.10"
    def request = Mock(HttpServletRequest) {
        it.getRemoteAddr() >> ip
    }

    def "save bet successfully"() {
        given:
        double odd = 1.1
        def gameName = "gameName"
        def bet = new Bet(name: gameName, type: 'win', odd: odd)

        and:
        gameRepository.findByName(gameName) >> new Game(odd: ['win': odd])

        when:
        def result = controller.postBet(bet, request)

        then:
        result
        1 * betRepository.save(bet)
    }

    def "do not save if no game found"() {
        given:
        def gameName = "gameName"
        def bet = new Bet(name: gameName)

        and:
        gameRepository.findByName(gameName) >> null

        when:
        def result = controller.postBet(bet, request)

        then:
        0 * betRepository.save(bet)
        !result
    }

    def "do not save if odd outdated"() {
        given:
        double betOdd = 1.1
        double gameOdd = 1.2
        def gameName = "gameName"
        def bet = new Bet(name: gameName, type: 'win', odd: betOdd)

        and:
        gameRepository.findByName(gameName) >> new Game(odd: ['win': gameOdd])

        when:
        def result = controller.postBet(bet, request)

        then:
        !result
        0 * betRepository.save(bet)
    }

    def "do not save if odd not found"() {
        given:
        def gameName = "gameName"
        def bet = new Bet(name: gameName, type: 'win', odd: 1.1)

        and:
        gameRepository.findByName(gameName) >> new Game()

        when:
        def result = controller.postBet(bet, request)

        then:
        !result
        0 * betRepository.save(bet)
    }

}
