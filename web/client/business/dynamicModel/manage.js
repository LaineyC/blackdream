define(
    ["business/module", "business/api"], function (module) {
        "use strict";

        module.controller("dynamicModelManageController", [
            "$scope", "$routeParams", "dynamicModelApi", "viewPage",
            function($scope, $routeParams, dynamicModelApi, viewPage){
                viewPage.setViewPageTitle("数据模型管理");
                var generatorId = $routeParams.generatorId;

                $scope.searchRequest = {page:1, pageSize:10,generatorId:generatorId};

                $scope.search = function(){
                    dynamicModelApi.search($scope.searchRequest).success(function(pagerResult){
                        $scope.pagerResult = pagerResult;
                    });
                };

                $scope.search();

            }
        ]);
    }
);
