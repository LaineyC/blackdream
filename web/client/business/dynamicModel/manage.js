define(
    ["business/module", "business/api"], function (module) {
        "use strict";

        module.controller("dynamicModelManageController", [
            "$scope", "$routeParams", "dynamicModelApi", "viewPage", "confirm",
            function($scope, $routeParams, dynamicModelApi, viewPage, confirm){
                viewPage.setViewPageTitle("数据模型管理");
                var generatorId = $routeParams.generatorId;

                $scope.searchRequest = {page:1, pageSize:10,generatorId:generatorId};

                $scope.search = function(){
                    dynamicModelApi.search($scope.searchRequest).success(function(pagerResult){
                        $scope.pagerResult = pagerResult;
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

                $scope.search();

            }
        ]);
    }
);
