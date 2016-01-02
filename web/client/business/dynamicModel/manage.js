define(
    ["business/module", "business/api"], function (module) {
        "use strict";

        module.controller("dynamicModelManageController", [
            "$scope", "$modal", "$routeParams", "dynamicModelApi",
            function($scope, $modal, $routeParams, dynamicModelApi){
                var generatorId = $routeParams.generatorId;

                $scope.searchRequest = {page:1, pageSize:10,generatorId:generatorId};

                $scope.pageSizeList = [10, 20, 50, 100];

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
