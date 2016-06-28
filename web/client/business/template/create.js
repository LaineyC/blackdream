define(
    ["business/module", "business/api"],
    function (module) {
        "use strict";

        module.controller("templateCreateController", [
            "$scope", "$routeParams", "location", "templateApi", "base64", "viewPage",
            function($scope, $routeParams, location, templateApi, base64, viewPage){
                viewPage.setViewPageTitle("模板文件新建");

                var generatorId = $routeParams.generatorId;

                $scope.template = {code:"hello blackdream"};

                $scope.createRequest = {generatorId:generatorId, templateFile:{name:"blackdream.vm",content:"hello blackdream"}};

                $scope.create = function(){
                    $scope.createRequest.templateFile.content = base64.encode($scope.template.code);
                    templateApi.create($scope.createRequest).success(function(template){
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
