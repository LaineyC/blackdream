define(
    [
        "framework/framework"
    ],
    function (module) {
    "use strict";
        module.controller("homeController", [
            "$scope", "generatorApi", "generatorInstanceApi", "security",
            function($scope, generatorApi, generatorInstanceApi, security){
                var user = security.getUser();
                generatorInstanceApi.search({page:1, pageSize:20}).success(function(pagerResult){
                    $scope.generatorInstances = pagerResult.records;
                });
                generatorApi.search({page:1, pageSize:20,developerId: user.id}).success(function(pagerResult){
                    $scope.generators = pagerResult.records;
                });
            }
    ]);
});