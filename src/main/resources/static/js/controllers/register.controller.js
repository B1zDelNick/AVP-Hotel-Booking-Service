
(function ()
{
    'use strict';

    angular
        .module('app')
        .controller('RegisterController', RegisterController);

    RegisterController.$inject = ['$scope', '$location'];

    function RegisterController($scope, $location)
    {
        $scope.message = "";
        $scope.errorCode = 0;
        $scope.toLogin = function ()
        {
            $location.url("/login");
        };

        $scope.hasError = function ()
        {
            console.log($location.search().error);

            var result = $location.search().error != null;

            if (result)
            {
                if ($location.search().error == 1)
                {
                    $scope.message = "Login and Password must be not empty";
                    $scope.errorCode = 1;
                }
                else if ($location.search().error == 2)
                {
                    $scope.message = "Not valid email";
                    $scope.errorCode = 2;
                }
                else
                {
                    $scope.message = "";
                    $scope.errorCode = 0;
                }
            }

            return result;
        }

        $scope.isError = function (test)
        {
            return test == $scope.errorCode;
        }
    }
})();