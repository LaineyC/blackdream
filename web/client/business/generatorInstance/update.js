define(
    ["business/module", "business/api"],
    function (module) {
        "use strict";

        module.controller("generatorInstanceUpdateController", [
            "$scope", "$routeParams", "location", "generatorInstanceApi",
            function($scope, $routeParams, location, generatorInstanceApi){
                $scope.updateRequest = {};

                var id = $routeParams.id;
                generatorInstanceApi.get({id: id}).success(function(generatorInstance){
                    angular.extend($scope.updateRequest, generatorInstance);
                });

                $scope.update = function(){
                    generatorInstanceApi.update($scope.updateRequest).success(function(){
                        location.go("/business/generatorInstance/manage");
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
