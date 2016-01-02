define(
    ["business/module", "business/api"],
    function (module) {
        "use strict";

        module.controller("generatorInstanceDetailController", [
            "$scope", "$routeParams", "location", "generatorInstanceApi",
            function($scope, $routeParams, location, generatorInstanceApi){
                var id = $routeParams.id;
                generatorInstanceApi.get({id: id}).success(function(generatorInstance){
                    $scope.generatorInstance = generatorInstance;
                });
            }
        ]);
    }
);
