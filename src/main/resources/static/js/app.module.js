
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

            $routeProvider.when('/about',
                {
                    templateUrl:'templates/about.html',
                    controller: function () {

                    }
                });

            $routeProvider.when('/bookings',
                {
                    templateUrl:'templates/bookings.html',
                    controller: function () {

                    }
                });

            $routeProvider.when('/contacts',
                {
                    templateUrl:'templates/contacts.html',
                    controller: function () {

                    }
                });

            $routeProvider.when('/register',
                {
                    templateUrl:'templates/register.html',
                    controller: 'RegisterController'
                });

            $routeProvider.when('/logout2',
                {
                    templateUrl:'templates/logout.html',
                    controller: 'LogoutController'
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

