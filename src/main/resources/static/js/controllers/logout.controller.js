
(function ()
{
    'use strict';

    angular
        .module('app')
        .controller('LogoutController', LogoutController);

    LogoutController.$inject = ['$scope','$location'];

    function LogoutController($scope, $location)
    {
        $scope.toLogin = function ()
        {
            $location.url("/login");
        };
        $scope.toHome = function ()
        {
            $location.url("/home");
        };
    }
})();