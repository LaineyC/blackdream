define(
    ["business/module", "business/api"],
    function (module) {
    "use strict";
        module.controller("homeController", [
            "$scope", "generatorApi", "generatorInstanceApi", "$uibModal", "viewPage",
            function($scope, generatorApi, generatorInstanceApi, $uibModal, viewPage){
                viewPage.setViewPageTitle("首页");

                generatorInstanceApi.authSearch({page:1, pageSize:10, sortField:"modifyDate", sortDirection:"DESC"}).success(function(pagerResult){
                    $scope.generatorInstances = pagerResult.records;
                });

                generatorApi.authSearch({page:1, pageSize:10, sortField:"instanceCount", sortDirection:"DESC"}).success(function(pagerResult){
                    $scope.generators = pagerResult.records;
                });

            }
        ]);
    }
);