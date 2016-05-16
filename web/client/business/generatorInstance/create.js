define(
    ["business/module", "business/api"],
    function (module) {
        "use strict";

        module.controller("generatorInstanceCreateController", [
            "$scope", "$routeParams", "location", "generatorInstanceApi", "generatorApi",  "viewPage",
            function($scope, $routeParams, location, generatorInstanceApi, generatorApi, viewPage){
                viewPage.setViewPageTitle("实例新建");

                var generatorId = $routeParams.generatorId;

                $scope.createRequest = {generatorId:generatorId};

                generatorApi.get({id:generatorId}).success(function(generator){
                    $scope.generator = generator;
                });

                $scope.create = function(){
                    generatorInstanceApi.create($scope.createRequest).success(function(generatorInstance){
                        location.go("/business/generatorInstance/detail/" + generatorInstance.id);
                    });
                };

                $scope.validateMessages = {
                    name:{
                        required:"必输项",
                        maxlength:"最长20位"
                    }
                };

                $scope.getMessage = function(field, $error, validateMessages){
                    for(var k in $error){
                        return validateMessages[field][k];
                    }
                };

            }
        ]);
    }
);
