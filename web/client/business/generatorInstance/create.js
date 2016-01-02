define(
    ["business/module", "business/api"],
    function (module) {
        "use strict";

        module.controller("generatorInstanceCreateController", [
            "$scope", "$routeParams", "location", "generatorInstanceApi",
            function($scope, $routeParams, location, generatorInstanceApi){
                var generatorId = $routeParams.generatorId;

                $scope.createRequest = {generatorId:generatorId};

                $scope.create = function(){
                    generatorInstanceApi.create($scope.createRequest).success(function(){
                        location.back();
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
