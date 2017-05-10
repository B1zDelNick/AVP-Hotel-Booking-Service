
(function ()
{
    'use strict';

    angular
        .module('app')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope','$location', '$http'];

    function HomeController($scope, $location, $http)
    {
        $scope.logged = false;
        $scope.toLogin = function ()
        {
            $location.url("/login");
        };
        $scope.isLog = function ()
        {
            //console.log($scope.logged);
            return $scope.logged;
        };

        init();

        function init()
        {
            isLogged();
        }

        function isLogged()
        {
            var url = "/isLogged";
            var loggedPromise = $http.post(url);
            $scope.logged = false;

            loggedPromise.then(
                function(response)
                {
                    //console.log(response.data);
                    $scope.logged = response.data.body != 'none';
                },
                function(error)
                {
                    console.log(error);
                });
        }
    }
})();