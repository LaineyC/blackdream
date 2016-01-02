define(
    ["business/module", "business/api"],
    function (module) {
        "use strict";

        module.controller("generatorDetailController", [
            "$scope", "$routeParams", "location", "generatorApi",
            function($scope, $routeParams, location, generatorApi){
                var id = $routeParams.id;
                generatorApi.get({id: id}).success(function(generator){
                    $scope.generator = generator;
                });
            }
        ]);
    }
);
