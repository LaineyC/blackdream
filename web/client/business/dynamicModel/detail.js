define(
    ["business/module", "business/api"],
    function (module) {
        "use strict";

        module.controller("dynamicModelDetailController", [
            "$scope", "$routeParams", "location", "dynamicModelApi", "viewPage",
            function($scope, $routeParams, location, dynamicModelApi, viewPage){
                viewPage.setViewPageTitle("数据模型详情");
                var id = $routeParams.id;

                dynamicModelApi.get({id: id}).success(function(dynamicModel){
                    $scope.dynamicModel = dynamicModel;
                });
            }
        ]);
    }
);
