define(
    ["business/module", "business/api"],
    function (module) {
        "use strict";

        module.controller("generatorUpdateController", [
            "$scope", "$routeParams", "location", "generatorApi", "viewPage",
            function($scope, $routeParams, location, generatorApi, viewPage){
                viewPage.setViewPageTitle("生成器修改");
                $scope.updateRequest = {};

                var id = $routeParams.id;
                generatorApi.get({id: id}).success(function(generator){
                    angular.extend($scope.updateRequest, generator);
                });

                $scope.update = function(){
                    generatorApi.update($scope.updateRequest).success(function(){
                        location.go("/business/generator/manage");
                    });
                };

                $scope.validateMessages = {
                    name:{
                        required:"必输项",
                        maxlength:"最长20位"
                    }
                };

                $scope.getMessage = function(field, $error, validateMessages){
                    for(var k in $error)
                        return validateMessages[field][k];
                };

            }
        ]);
    }
);
