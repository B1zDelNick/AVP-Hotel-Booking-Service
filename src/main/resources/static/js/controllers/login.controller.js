
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
        $scope.hasErrors = function()
        {
            return $scope.errors;
        };

        $scope.login = function()
        {
            /*console.log(JSON.stringify($scope.credentials));

            var url = "/auth/login";
            var result = false;
            var promise = $http.post(url, JSON.stringify($scope.credentials));

            promise.then(
                function(response)
                {
                    console.log(response.data);
                    result = response.data.body == 'ERROR';

                    //if (!result) $location.url("/");
                },
                function(error)
                {
                    alert( "Exception details: " + JSON.stringify({data: error}));
                    console.log(error);

                    result = true;
                });

            $scope.errors = result;*/
        };
    }
})();