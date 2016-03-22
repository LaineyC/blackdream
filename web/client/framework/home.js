define(
    [
        "framework/framework"
    ],
    function (module) {
    "use strict";
        module.controller("homeController", [
            "$scope", "generatorApi", "generatorInstanceApi", "security", "viewPage",
            function($scope, generatorApi, generatorInstanceApi, security, viewPage){
                viewPage.setViewPageTitle("首页");
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