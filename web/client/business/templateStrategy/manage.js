define(
    ["business/module", "business/api"], function (module) {
        "use strict";

        module.controller("templateStrategyManageController", [
            "$scope", "$modal", "$routeParams", "templateStrategyApi", "viewPage",
            function($scope, $modal, $routeParams, templateStrategyApi, viewPage){
                viewPage.setViewPageTitle("策略文件管理");
                var generatorId = $routeParams.generatorId;

                $scope.searchRequest = {page:1, pageSize:10,generatorId:generatorId};

                $scope.pageSizeList = [10, 20, 50, 100];

                $scope.search = function(){
                    templateStrategyApi.search($scope.searchRequest).success(function(pagerResult){
                        $scope.pagerResult = pagerResult;
                    });
                };

                $scope.search();

            }
        ]);
    }
);
