define(
    ["business/module", "business/api"],
    function (module) {
        "use strict";

        module.controller("userDetailController", [
            "$scope", "$routeParams", "location", "userApi","viewPage",
            function($scope, $routeParams, location, userApi, viewPage){
                viewPage.setViewPageTitle("用户详情");
                var id = $routeParams.id;
                userApi.get({id: id}).success(function(user){
                    $scope.user = user;
                });
            }
        ]);
    }
);
