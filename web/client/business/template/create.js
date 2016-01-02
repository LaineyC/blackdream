define(
    ["business/module", "business/api"],
    function (module) {
        "use strict";

        module.controller("templateCreateController", [
            "$scope", "$routeParams", "location", "templateApi",
            function($scope, $routeParams, location, templateApi){
                var generatorId = $routeParams.generatorId;

                $scope.createRequest = {generatorId:generatorId};

                $scope.create = function(){
                    templateApi.create($scope.createRequest).success(function(){
                        location.go("/business/template/manage/" + generatorId);
                    });
                };

                $scope.templateControl = {
                    select:function($file){
                        var fileReader = new FileReader();
                        fileReader.onload = function(event) {
                            var result = event.target.result;
                            $scope.createRequest.templateFile = {
                                name:$file.name,
                                type:$file.type,
                                content:result.substring(result.indexOf(",") + 1)
                            };
                            $scope.templateControl.templateName = $file.name;
                            $scope.templateControl.templateImage = "/client/business/template/select.jpg";
                        };
                        fileReader.readAsDataURL($file);
                    }
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
