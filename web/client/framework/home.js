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

                generatorInstanceApi.authSearch({page:1, pageSize:10}).success(function(pagerResult){
                    $scope.generatorInstances = pagerResult.records;
                });

                generatorApi.authSearch({page:1, pageSize:10}).success(function(pagerResult){
                    $scope.generators = pagerResult.records;
                });

            }
    ]);
});