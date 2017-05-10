
(function()
{
    'use strict';

    angular.module('app', ['ngRoute'])
        .config(function ($routeProvider, $locationProvider)
        {
            $routeProvider.when('/login',
                {
                    templateUrl:'templates/login.html',
                    controller: 'LoginController'
                });

            $routeProvider.when('/home',
                {
                    templateUrl:'templates/default.html',
                    controller: 'HomeController'
                });

            $routeProvider.otherwise({redirectTo: '/home'});
            $locationProvider.html5Mode(
                {
                    enabled: true,
                    requireBase: false
                });
        });
})();

