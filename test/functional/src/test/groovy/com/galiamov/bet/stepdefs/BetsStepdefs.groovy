package com.galiamov.bet.stepdefs

import static cucumber.api.groovy.EN.Then
import static cucumber.api.groovy.EN.When
import static cucumber.api.groovy.Hooks.After;

When(~'^Place bet on "([^"]*)" game$') { String name ->
    bet.add(name)
}

When(~'^Place bet on "([^"]*)" game with IP "([^"]*)"$') { String name, String ip ->
    bet.addWithIP(name, ip)
}

Then(~'^Bet on "([^"]*)" not placed$') { String name ->
    bet.tryAdd(name)
}

Then(~'^Bet placed$') { ->
    bet.betPresent()
}

Then(~'^Delete bet by timestamp successfully$') { ->
    bet.delete()
}

Then(~'^Total bets count by IP "([^"]*)" is "([^"]*)"$') { String ip, Integer count ->
    bet.reportsFromIp(ip, count)
}
Then(~'^Total bets count by game "([^"]*)" is "([^"]*)"$') { String name, Integer count ->
    bet.reportsByGame(name, count)
}

Then(~'^All bets by name "([^"]*)" are in the report') { String name ->
    bet.reportsByGame(name)
}

After {
    game.clear()
    bet.clear()
}