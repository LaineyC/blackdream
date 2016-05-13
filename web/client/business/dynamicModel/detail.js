define(
    ["business/module", "business/api"],
    function (module) {
        "use strict";

        module.controller("dynamicModelDetailController", [
            "$scope", "$routeParams", "location", "dynamicModelApi", "viewPage", "confirm",
            function($scope, $routeParams, location, dynamicModelApi, viewPage, confirm){
                viewPage.setViewPageTitle("数据模型详情");
                var id = $routeParams.id;

                dynamicModelApi.get({id: id}).success(function(dynamicModel){
                    $scope.dynamicModel = dynamicModel;
                });

                $scope.delete = function(dynamicModel){
                    if(dynamicModel.generator.isApplied){
                        return;
                    }
                    confirm.open({
                        title:"删除",
                        message:"确定删除【" + dynamicModel.name +"】？",
                        confirm:function(){
                            dynamicModelApi.delete({id:dynamicModel.id}).success(function(){
                                location.go("/business/dynamicModel/manage/" + dynamicModel.generator.id);
                            });
                        }
                    });
                };
            }
        ]);
    }
);
