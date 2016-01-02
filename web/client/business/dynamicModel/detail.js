define(
    ["business/module", "business/api"],
    function (module) {
        "use strict";

        module.controller("dynamicModelDetailController", [
            "$scope", "$routeParams", "location", "dynamicModelApi",
            function($scope, $routeParams, location, dynamicModelApi){
                var id = $routeParams.id;

                dynamicModelApi.get({id: id}).success(function(dynamicModel){
                    $scope.dynamicModel = dynamicModel;
                });
            }
        ]);
    }
);
