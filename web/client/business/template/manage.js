define(
    ["business/module", "business/api"],
    function (module) {
        "use strict";

        module.controller("templateManageController", [
            "$scope", "$routeParams", "templateApi", "viewPage", "confirm",
            function($scope, $routeParams, templateApi, viewPage, confirm){
                viewPage.setViewPageTitle("模板文件管理");
                var generatorId = $routeParams.generatorId;

                $scope.queryRequest = {generatorId:generatorId};

                $scope.query = function(){
                    templateApi.query($scope.queryRequest).success(function(templates){
                        $scope.templates = templates;
                    });
                };

                $scope.delete = function(template){
                    confirm.open({
                        title:"删除",
                        message:"确定删除【" + template.name +"】？",
                        confirm:function(){
                            templateApi.delete({id:template.id}).success(function(){
                                $scope.query();
                            });
                        }
                    });
                };

                $scope.sortableOptions = {
                    update: function(e, ui) {
                        templateApi.sort({
                            id:ui.item.sortable.model.id,
                            fromIndex:ui.item.sortable.index,
                            toIndex:ui.item.sortable.dropindex
                        });
                    },
                    stop: function(e, ui) {

                    }
                };

                $scope.query();

            }
        ]);
    }
);
