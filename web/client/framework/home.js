define(
    [
        "framework/framework"
    ],
    function (module) {
    "use strict";
        module.controller("homeController", [
            "$scope", "generatorApi", "generatorInstanceApi", "security", "$uibModal", "viewPage",
            function($scope, generatorApi, generatorInstanceApi, security, $uibModal, viewPage){
                viewPage.setViewPageTitle("首页");

                var user = security.getUser();

                generatorInstanceApi.search({page:1, pageSize:10}).success(function(pagerResult){
                    $scope.generatorInstances = pagerResult.records;
                });

                generatorApi.search({page:1, pageSize:10,developerId: user.id}).success(function(pagerResult){
                    $scope.generators = pagerResult.records;
                });
            }
    ]);
});