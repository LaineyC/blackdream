define(
    ["business/module", "business/api"], function (module) {
        "use strict";

        module.controller("templateStrategyManageController", [
            "$scope", "$routeParams", "templateStrategyApi", "viewPage", "confirm",
            function($scope, $routeParams, templateStrategyApi, viewPage, confirm){
                viewPage.setViewPageTitle("生成策略管理");
                var generatorId = $routeParams.generatorId;

                $scope.searchRequest = {page:1, pageSize:10,generatorId:generatorId};

                $scope.search = function(){
                    templateStrategyApi.search($scope.searchRequest).success(function(pagerResult){
                        $scope.pagerResult = pagerResult;
                    });
                };

                $scope.delete = function(templateStrategy){
                    confirm.open({
                        title:"删除",
                        message:"确定删除【" + templateStrategy.name +"】？",
                        confirm:function(){
                            templateStrategyApi.delete({id:templateStrategy.id}).success(function(){
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
