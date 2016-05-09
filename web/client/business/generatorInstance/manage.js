define(
    ["business/module", "business/api"], function (module) {
        "use strict";

        module.controller("generatorInstanceManageController", [
            "$scope", "generatorInstanceApi", "viewPage", "confirm",
            function($scope, generatorInstanceApi, viewPage, confirm){
                viewPage.setViewPageTitle("实例管理");
                $scope.searchRequest = {page:1, pageSize:10};

                $scope.search = function(){
                    generatorInstanceApi.authSearch($scope.searchRequest).success(function(pagerResult){
                        $scope.pagerResult = pagerResult;
                    });
                };

                $scope.delete = function(generatorInstance){
                    confirm.open({
                        title:"删除",
                        message:"是否删除【" + generatorInstance.name +"】？",
                        confirm:function(){
                            generatorInstanceApi.delete({id:generatorInstance.id}).success(function(){
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
