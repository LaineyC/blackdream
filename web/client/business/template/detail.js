define(
    ["business/module", "business/api"],
    function (module) {
        "use strict";

        module.controller("templateDetailController", [
            "$scope", "$routeParams", "location", "templateApi", "viewPage", "confirm", "$cookies",
            function($scope, $routeParams, location, templateApi, viewPage, confirm, $cookies){
                viewPage.setViewPageTitle("模板文件详情");
                $scope.template = {};

                var id = $routeParams.id;
                templateApi.get({id: id}).success(function(template){
                    $scope.template = template;
                });

                templateApi.codeGet({id:id}).success(function(code){
                    $scope.template.code = code;
                });

                $scope.aceTheme = $cookies.get("aceTheme") || "github";

                $scope.delete = function(template){
                    confirm.open({
                        title:"删除",
                        message:"确定删除【" + template.name +"】？",
                        confirm:function(){
                            templateApi.delete({id:template.id}).success(function(){
                                location.go("/business/template/manage/" + template.generator.id)
                            });
                        }
                    });
                };

            }
        ]);
    }
);
