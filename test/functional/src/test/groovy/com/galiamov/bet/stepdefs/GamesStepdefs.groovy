package com.galiamov.bet.stepdefs

import static cucumber.api.groovy.EN.*
import static cucumber.api.groovy.Hooks.After

When(~'^Add new "([^"]*)" game$') { String name ->
    game.add(name)
}

Then(~'^Game "([^"]*)" present$') { String name ->
    game.gamePresent(name)
}

Then(~'^Games "([^"]*)" present$') { String names ->
    game.gamesPresent(names)
}

And(~'^Update game "([^"]*)" with new odds$') { String name ->
    game.updateGame(name)
}

Then(~'^Game "([^"]*)" updated$') { String name ->
    game.gameUpdated(name)
}

After {
    game.clear()
}