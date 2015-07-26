package com.galiamov.bet.world

import com.gmongo.GMongo
import com.netflix.governator.annotations.AutoBindSingleton
import groovyx.net.http.RESTClient

import static groovyx.net.http.ContentType.JSON

@AutoBindSingleton
public class Game {

    def client = new RESTClient('http://localhost:8080/')

    def gmongo = new GMongo("localhost:27017")
    def db = gmongo.getDB("test")

    def gameRepository = [:]

    Random random = new Random()

    def clear() {
        db.game.drop()
    }

    def add(name) {
        def game = createGame(name)

        gameRepository << [(name): game]

        db.game.insert(game)
        println game
    }

    def gamePresent(name) {
        def response = client.get(path: "/games/$name")

        def data = response.data

        assert response.status == 200
        def game = gameRepository[(name)]
        compareGames(data, game)
    }

    def gamesPresent(String names) {
        List gameNames = names.split(',')
        def response = client.get(path: "/games")

        def data = response.data

        assert response.status == 200
        assert data.size() == gameNames.size()
        data.each {
            def game = gameRepository[(it.name)]
            compareGames(game, it)
        }
    }

    def updateGame(name) {
        def newGame = createGame(name)
        gameRepository[(name)] = newGame
        newGame.odd.remove("name")
        def body = [name: name, odd: newGame.odd]
        def response = client.post(
                path: "/games/update",
                body: body,
                requestContentType: JSON
        )

        def data = response.data

        assert response.status == 200
        assert data.name == name
        compareOdd(data.odd, newGame.odd)
    }

    def gameUpdated(name) {
        gamePresent(name)
    }

    private static compareGames(ethalonGmae, gameFromServer) {
        assert ethalonGmae != null
        assert gameFromServer != null
        assert ethalonGmae.name == gameFromServer.name
        compareOdd(ethalonGmae.odd, gameFromServer.odd)
    }

    private static compareOdd(oldOdd, newOdd) {
        assert oldOdd.win == newOdd.win
        assert oldOdd.draw == newOdd.draw
        assert oldOdd.lose == newOdd.lose
    }

    private createGame(name) {
        [name    : name,
         datetime: System.currentTimeMillis(),
         odd     : [
                 name: "1x2",
                 win : getRandomCoefficient(),
                 draw: getRandomCoefficient(),
                 lose: getRandomCoefficient()
         ]
        ]
    }

    private double getRandomCoefficient() {
        double rangeMin = 0.1
        double rangeMax = 20.9
        double randomValue = rangeMin + (rangeMax - rangeMin) * random.nextDouble()
        return randomValue
    }

}
