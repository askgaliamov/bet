(function (angular) {
    angular.module("betApp.controllers")
        .controller("GameCtrl", function ($scope, GameService, GameSocketService) {

            $scope.infoBox = "";
            $scope.bet = false;

            $scope.games = [];
            $scope.loadGames = function () {
                $scope.games = GameService.games().success(function (result) {
                        $scope.games = result;
                    }
                )
            };

            $scope.showBettingSlip = function (game, type) {
                $scope.infoBox = "";
                $scope.bet = [];
                $scope.bet.name = game.name;
                $scope.bet.type = type;
                $scope.bet.odd = game.odd[type];
                $scope.bet.amount = "20 USD";
            };

            $scope.placeBet = function () {
                $scope.infoBox = "";

                var bet = {
                    "name": $scope.bet.name,
                    "type": $scope.bet.type,
                    "odd": $scope.bet.odd,
                    "amount": $scope.bet.amount
                };

                GameService.bet(bet).then(function (payload) {
                    if (payload.data) {
                        $scope.infoBox = "the bet is placed";
                        $scope.bet = false;
                    } else {
                        $scope.infoBox = "new coefficient";
                        var gamePromise = GameService.game($scope.bet.name);
                        gamePromise.then(function (result) {
                                $scope.bet.odd = result.data.odd[$scope.bet.type];
                            }
                        );
                    }
                });
            };

            $scope.sendUpdate = function (game) {
                var gameUpdate = {
                    name: game.name,
                    odd: {
                        win: game.odd['win'],
                        draw: game.odd['draw'],
                        lose: game.odd['lose']
                    }
                };
                GameSocketService.send(gameUpdate);
            };

            GameSocketService.receive().then(null, null, function (update) {
                update = JSON.parse(update);
                $scope.games.forEach(function (game) {
                    if (game.name == update.name) {
                        for (var outcome in update.odd) {
                            game.odd[outcome] = update.odd[outcome];
                        }
                    }
                });
            });

        });
}(angular));