define(
    ["business/module", "business/api"],
    function (module) {
        "use strict";

        module.controller("generatorInstanceDetailController", [
            "$scope", "$routeParams", "location", "generatorInstanceApi", "viewPage",
            function($scope, $routeParams, location, generatorInstanceApi, viewPage){
                viewPage.setViewPageTitle("实例详情");
                var id = $routeParams.id;
                generatorInstanceApi.get({id: id}).success(function(generatorInstance){
                    $scope.generatorInstance = generatorInstance;
                });
            }
        ]);
    }
);
