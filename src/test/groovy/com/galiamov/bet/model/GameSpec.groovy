package com.galiamov.bet.model

import spock.lang.Specification


class GameSpec extends Specification {

    def "update odd"() {
        given:
        def game = new Game(odd: ['win': 1.2, 'lose': 3.2])

        when:
        game.updateOdd(['win': 2.3, 'draw': 4.2])

        def odd = game.getOdd()

        then:
        odd.get('win') == 2.3
        odd.get('lose') == 3.2
        odd.get('draw') == 4.2
    }

    def "is odd present for bet type"() {
        given:
        def game = new Game(odd: ['win': 1.2])

        expect:
        game.isOddPresentFor('win')
        !game.isOddPresentFor('lose')
    }

    def "is oddEquals to"() {
        given:
        def game = new Game(odd: ['win': (double) 1.2])

        expect:
        game.isOddEqualsTo('win', 1.2)
        !game.isOddEqualsTo('win', 1.3)
    }
}
