(function (angular) {
    angular.module("betApp.controllers", []);
    angular.module("betApp.services", []);
    angular.module("betApp", ["ngResource", "betApp.controllers", "betApp.services"]);
}(angular));