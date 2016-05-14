define(
    ["business/module", "business/api"], function (module) {
        "use strict";

        module.controller("dynamicModelManageController", [
            "$scope", "$routeParams", "dynamicModelApi", "viewPage", "confirm",
            function($scope, $routeParams, dynamicModelApi, viewPage, confirm){
                viewPage.setViewPageTitle("数据模型管理");
                var generatorId = $routeParams.generatorId;

                $scope.queryRequest = {generatorId:generatorId};

                $scope.query = function(){
                    dynamicModelApi.query($scope.queryRequest).success(function(dynamicModels){
                        $scope.dynamicModels = dynamicModels;
                    });
                };

                $scope.delete = function(dynamicModel){
                    if(dynamicModel.generator.isApplied){
                        return;
                    }
                    confirm.open({
                        title:"删除",
                        message:"确定删除【" + dynamicModel.name +"】？",
                        confirm:function(){
                            dynamicModelApi.delete({id:dynamicModel.id}).success(function(){
                                $scope.search();
                            });
                        }
                    });
                };

                $scope.query();

            }
        ]);
    }
);
