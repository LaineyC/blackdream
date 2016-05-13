define(
    ["business/module", "business/api"],
    function (module) {
        "use strict";

        module.controller("generatorInstanceDetailController", [
            "$scope", "$routeParams", "location", "generatorInstanceApi", "viewPage", "confirm",
            function($scope, $routeParams, location, generatorInstanceApi, viewPage, confirm){
                viewPage.setViewPageTitle("实例详情");
                var id = $routeParams.id;

                generatorInstanceApi.get({id: id}).success(function(generatorInstance){
                    $scope.generatorInstance = generatorInstance;
                });

                $scope.delete = function(generatorInstance){
                    confirm.open({
                        title:"删除",
                        message:"确定删除【" + generatorInstance.name +"】？",
                        confirm:function(){
                            generatorInstanceApi.delete({id:generatorInstance.id}).success(function(){
                                location.go("/business/generatorInstance/manage");
                            });
                        }
                    });
                };

            }
        ]);
    }
);
