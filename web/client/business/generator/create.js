define(
    ["business/module", "business/api"],
    function (module) {
        "use strict";

        module.controller("generatorCreateController", [
            "$scope", "alert", "location", "generatorApi",
            function($scope, alert, location, generatorApi){

                $scope.createRequest = {isOpen : false};

                $scope.create = function(){
                    generatorApi.create($scope.createRequest).success(function(){
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
