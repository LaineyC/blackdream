define(
    ["business/module", "business/api"],
    function (module) {
    "use strict";
        module.controller("statisticController", [
            "$scope", "systemApi", "viewPage",
            function($scope, systemApi, viewPage){
                viewPage.setViewPageTitle("数据统计");

                systemApi.statistic({}).success(function(result){
                    $scope.result = result;
                });

            }
        ]);
    }
);