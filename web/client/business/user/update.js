define(
    ["business/module", "business/api"],
    function (module) {
        "use strict";

        module.controller("userUpdateController", [
            "$scope", "$routeParams", "location", "userApi",
            function($scope, $routeParams, location, userApi){

                $scope.updateRequest = {isResetPassword:false};

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
