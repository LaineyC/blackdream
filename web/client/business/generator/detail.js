define(
    ["business/module", "business/api"],
    function (module) {
        "use strict";

        module.controller("generatorDetailController", [
            "$scope", "$routeParams", "location", "generatorApi", "viewPage",
            function($scope, $routeParams, location, generatorApi, viewPage){
                viewPage.setViewPageTitle("生成器详情");
                var id = $routeParams.id;
                generatorApi.get({id: id}).success(function(generator){
                    $scope.generator = generator;
                });

                $scope.export = function(generator){
                    generatorApi.export({id: generator.id});
                };
            }
        ]);
    }
);
