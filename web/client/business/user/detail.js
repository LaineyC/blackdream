define(
    ["business/module", "business/api"],
    function (module) {
        "use strict";

        module.controller("userDetailController", [
            "$scope", "$routeParams", "location", "userApi",
            function($scope, $routeParams, location, userApi){
                var id = $routeParams.id;
                userApi.get({id: id}).success(function(user){
                    $scope.user = user;
                });
            }
        ]);
    }
);
