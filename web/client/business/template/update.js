define(
    ["business/module", "business/api"],
    function (module) {
        "use strict";

        module.controller("templateUpdateController", [
            "$scope", "$routeParams", "location", "templateApi", "viewPage",
            function($scope, $routeParams, location, templateApi, viewPage){
                viewPage.setViewPageTitle("模板修改");
                $scope.updateRequest = {};

                var id = $routeParams.id;
                templateApi.get({id: id}).success(function(template){
                    angular.extend($scope.updateRequest, template);
                });

                $scope.templateControl = {
                    templateImage: "/client/business/template/select.jpg",
                    select:function($file){
                        var fileReader = new FileReader();
                        fileReader.onload = function(event) {
                            var result = event.target.result;
                            $scope.updateRequest.templateFile = {
                                name:$file.name,
                                type:$file.type,
                                content:result.substring(result.indexOf(",") + 1)
                            };
                            $scope.templateControl.templateName = $file.name;
                        };
                        $scope.templateUpdateForm.$setDirty();
                        fileReader.readAsDataURL($file);
                    }
                };

                $scope.update = function(){
                    templateApi.update($scope.updateRequest).success(function(){
                        location.go("/business/template/manage/" + $scope.updateRequest.generator.id);
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
