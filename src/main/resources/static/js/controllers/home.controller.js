
(function ()
{
    'use strict';

    angular
        .module('app')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope','$http'];

    function HomeController($scope, $http)
    {
        //vm.bookings = [];
        $scope.logged = false;
        //vm.getAffordable = getAffordable;
        //vm.deleteBooking = deleteBooking;

        init();

        function init()
        {
            isLogged();
        }

        function isLogged()
        {
            var url = "/auth/isLogged";
            var loggedPromise = $http.post(url);
            var result = false;

            loggedPromise.then(
                function(response)
                {
                    console.log(response.data);
                    result = response.data.body != 'none';
                },
                function(error)
                {
                    console.log(error);
                });

            $scope.logged = result;
        }

        /*function getAffordable()
        {
            var url = "/bookings/affordable/" + 100;
            var bookingsPromise = $http.post(url);
            bookingsPromise.then(function(response)
            {
                vm.bookings = response.data;
            });
        }

        function deleteBooking(id)
        {
            console.log(id);

            var url = "/bookings/delete/" + id;
            $http.post(url).then(function(response)
            {
                vm.bookings = response.data;
            });
        }*/
    }
})();