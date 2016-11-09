define(
    ["business/module", "business/api"],
    function (module) {
        "use strict";

        module.controller("userUpdateController", [
            "$scope", "$routeParams", "location", "userApi","viewPage",
            function($scope, $routeParams, location, userApi, viewPage){
                viewPage.setViewPageTitle("用户修改");

                $scope.updateRequest = {};

                var id = $routeParams.id;
                userApi.get({id: id}).success(function(user){
                    angular.extend($scope.updateRequest, user);
                });

                $scope.update = function(){
                    userApi.update($scope.updateRequest).success(function(){
                        location.go("/business/user/manage");
                    });
                };

            }
        ]);
    }
);
