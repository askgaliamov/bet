package com.galiamov.bet.world

import com.gmongo.GMongo
import com.netflix.governator.annotations.AutoBindSingleton
import groovyx.net.http.RESTClient

import static groovyx.net.http.ContentType.JSON

@AutoBindSingleton
public class Bet {

    def client = new RESTClient('http://localhost:8080/')

    def gmongo = new GMongo("localhost:27017")
    def db = gmongo.getDB("test")

    def betRepository = []

    static Random random = new Random()

    def clear() {
        db.bet.drop()
        betRepository.clear()
    }

    def add(name) {
        def game = getGame(name)
        def bet = createBet(game)
        assert placeBet(bet) == true
    }

    def addWithIP(name, ip) {
        add(name)
        def betStored = db.bet.findOne(name: name)
        betStored.ip = ip
        db.bet.save(betStored)
    }

    def tryAdd(name) {
        def game = getGame(name)
        def newOdd = game.odd.win + 1
        def bet = createBet(game, newOdd)
        assert placeBet(bet) == false
    }

    def betPresent() {
        def betStored = db.bet.findOne(name: betRepository.first().name)
        compareBets(betStored, betRepository.first())
    }

    def delete() {
        def betStored = db.bet.findOne(name: betRepository.first().name)
        long timestamp = betStored.timestamp
        def deleteResponse = client.delete(path: "/bet", query: [timestamp: timestamp])
        assert deleteResponse.data.size == 1
    }

    def reportsFromIp(String ip, int count) {
        def response = client.get(
                path: "/reports/count/ip",
                query: [ip: ip])
        assert response.data.count == count
    }

    def reportsByGame(String name, int count) {
        def response = client.get(
                path: "/reports/count/name",
                query: [name: name])
        assert response.data.count == count
    }

    def reportsByGame(String name) {
        def response = client.get(
                path: "/reports/bets",
                query: [name: name])

        response.data.each {
            def amount = it.amount
            compareBets(it, betRepository.find({ it.name == name && it.amount == amount })
            )
        }
    }

    private static createBet(game, odd = game.odd.win) {
        def amount = createRandomAmount()
        return [
                id    : game.id,
                name  : game.name,
                type  : 'win',
                odd   : odd,
                amount: amount
        ]
    }

    private static String createRandomAmount() {
        int someAmount = random.nextInt(10000)
        return "$someAmount USD"
    }

    private getGame(name) {
        def gameResponse = client.get(path: "/games/$name")
        def game = gameResponse.data
        return game
    }

    private placeBet(bet) {
        betRepository.add(bet)
        def betResponse = client.post(
                path: "/bet",
                body: bet,
                requestContentType: JSON)
        assert betResponse.status == 200
        return betResponse.data
    }

    private static compareBets(ethalon, stored) {
        assert ethalon.name == stored.name
        assert ethalon.type == stored.type
        assert ethalon.odd == stored.odd
        assert ethalon.amount == stored.amount
    }

}
