Feature: Bets

  In order to place bets
  By request
  I have to be able place bet

  Scenario: Place bet
    Given Add new "Russia - France" game
    When Place bet on "Russia - France" game
    Then Bet placed

  Scenario: Do not place bet if odd changed
    Given Add new "Russia - France" game
    And Update game "Russia - France" with new odds
    Then Bet on "Russia - France" not placed

  Scenario: Delete bets by timestamp
    Given Add new "Russia - France" game
    When Place bet on "Russia - France" game
    And Bet placed
    Then Delete bet by timestamp successfully