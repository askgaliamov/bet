Feature: Games

  In order to place bets
  By request
  I should be able manage game list

  Scenario: Getting all games
    When Add new "Russia - France" game
    Then Add new "Germany - Italy" game
    Then Games "Russia - France,Germany - Italy" present

  Scenario: Getting specific game by name
    When Add new "Germany - Italy" game
    Then Game "Germany - Italy" present

  Scenario: Update odd of game
    When Add new "Germany - Italy" game
    And Update game "Germany - Italy" with new odds
    Then Game "Germany - Italy" updated
