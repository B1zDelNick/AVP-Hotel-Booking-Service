
(function ()
{
    'use strict';

    angular
        .module('app')
        .controller('MainController', MainController);

    MainController.$inject = ['$scope','$location', '$document'];

    function MainController($scope, $location, $document)
    {
        $scope.toLogin = function ()
        {
            $location.url("/login");
        };

        $scope.toLogout = function ()
        {
            $location.url("/logout");
        };

        $scope.toHome = function ()
        {
            $location.url("/home");
        };

        $scope.toAbout = function ()
        {
            $location.url("/about");
        };

        $scope.toContacts = function ()
        {
            $location.url("/contacts");
        };

        $scope.toBookings = function ()
        {
            $location.url("/bookings");
        };

        $scope.selectMe = function (event)
        {
            var home = angular.element($document[0].querySelector('#home-btn'));
            home.removeClass('active');
            var about = angular.element($document[0].querySelector('#about-btn'));
            about.removeClass('active');
            var contacts = angular.element($document[0].querySelector('#contacts-btn'));
            contacts.removeClass('active');

            $(event.target).parent().addClass('active');
        }
    }
})();