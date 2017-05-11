
(function ()
{
    'use strict';

    angular
        .module('app')
        .controller('LoginController', LoginController);

    LoginController.$inject = ['$scope','$http', '$location'];

    function LoginController($scope, $http, $location)
    {
        $scope.errors = false;
        $scope.credentials = {
            username: "",
            password: ""
        };

        $scope.toRegister = function ()
        {
            $location.url("/register");
        };

        $scope.hasError = function ()
        {
            console.log($location.search().error);

            return $location.search().error != null
        }
    }
})();