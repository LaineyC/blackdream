define(
    ["business/module", "business/api"],
    function (module) {
        "use strict";

        module.controller("templateUpdateController", [
            "$scope", "$routeParams", "location", "templateApi", "base64", "viewPage",
            function($scope, $routeParams, location, templateApi, base64, viewPage){
                viewPage.setViewPageTitle("模板文件修改");

                var id = $routeParams.id;

                $scope.template = {};

                $scope.upadateRequest = {templateFile:{}};

                templateApi.get({id: id}).success(function(template){
                    $scope.updateRequest = template;
                    $scope.updateRequest.templateFile = {};
                });

                templateApi.codeGet({id:id}).success(function(code){
                    $scope.template.code = code;
                });

                $scope.update = function(){
                    $scope.updateRequest.templateFile.content = base64.encode($scope.template.code);
                    templateApi.update($scope.updateRequest).success(function(template){
                        location.go("/business/template/manage/" + template.generator.id);
                    });
                };

                $scope.validateMessages = {
                    name:{
                        required:"必输项",
                        maxlength:"最长32位"
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
