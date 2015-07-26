(function (angular) {

    angular.module("betApp.services")
        .service("GameService", function ($q, $timeout, $http) {

            var service = [];

            service.games = function () {
                return $http.get('/games');
            };

            service.bet = function (bet) {
                return $http.post('/bet', bet);
            };

            service.game = function (name) {
                return $http.get('/games/' + name);
            };

            return service;
        })

        .service("GameSocketService", function ($q, $timeout) {

            var service = {}, listener = $q.defer(), socket = {
                client: null,
                stomp: null
            };

            service.RECONNECT_TIMEOUT = 30000;
            service.SOCKET_URL = "/betting_update";
            service.BETS_TOPIC = "/topic/new_bets";
            service.BETS_BROKER = "/games/update";

            service.receive = function () {
                return listener.promise;
            };

            service.send = function (gameUpdate) {
                socket.stomp.send(
                    service.BETS_BROKER,
                    {priority: 9},
                    JSON.stringify(gameUpdate)
                );
            };

            var reconnect = function () {
                $timeout(function () {
                    initialize();
                }, this.RECONNECT_TIMEOUT);
            };

            var getMessage = function (data) {
                return data;
            };

            var startListener = function () {
                socket.stomp.subscribe(service.BETS_TOPIC, function (data) {
                    listener.notify(getMessage(data.body));
                });
            };

            var initialize = function () {
                socket.client = new SockJS(service.SOCKET_URL);
                socket.stomp = Stomp.over(socket.client);
                socket.stomp.connect({}, startListener);
                socket.stomp.onclose = reconnect;
            };

            initialize();
            return service;
        });
}(angular));