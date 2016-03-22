define(
    ["business/module", "business/api"], function (module) {
        "use strict";

        module.controller("generatorInstanceManageController", [
            "$scope", "$modal", "generatorInstanceApi", "viewPage",
            function($scope, $modal, generatorInstanceApi, viewPage){
                viewPage.setViewPageTitle("实例管理");
                $scope.searchRequest = {page:1, pageSize:10};

                $scope.pageSizeList = [10, 20, 50, 100];

                $scope.search = function(){
                    generatorInstanceApi.search($scope.searchRequest).success(function(pagerResult){
                        $scope.pagerResult = pagerResult;
                    });
                };

                $scope.search();

            }
        ]);
    }
);
