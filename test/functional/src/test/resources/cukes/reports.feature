Feature: Reports

  In get some information about bets
  By request
  I should be able get different reports

  Scenario: Get total bets count by IP
    When Add new "Russia - France" game
    And Add new "Germany - Russia" game
    And Add new "Italy - Russia" game
    And Place bet on "Russia - France" game
    And Place bet on "Germany - Russia" game with IP "127.10.10.10"
    And Place bet on "Italy - Russia" game with IP "127.10.10.10"
    Then Total bets count by IP "127.10.10.10" is "2"

  Scenario: Get total bets count by game
    When Add new "Russia - France" game
    And Add new "Germany - Russia" game
    And Place bet on "Russia - France" game
    And Place bet on "Germany - Russia" game
    And Place bet on "Germany - Russia" game
    Then Total bets count by game "Germany - Russia" is "2"

  Scenario: Get all bets by game
    When Add new "Russia - France" game
    And Add new "Germany - Russia" game
    And Place bet on "Russia - France" game
    And Place bet on "Germany - Russia" game
    And Place bet on "Germany - Russia" game
    Then All bets by name "Germany - Russia" are in the report
