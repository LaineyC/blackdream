define(
    ["business/module", "business/api"],
    function (module) {
        "use strict";

        module.controller("generatorDetailController", [
            "$scope", "$routeParams", "location", "generatorApi", "viewPage", "confirm",
            function($scope, $routeParams, location, generatorApi, viewPage, confirm){
                viewPage.setViewPageTitle("生成器详情");
                var id = $routeParams.id;
                generatorApi.get({id: id}).success(function(generator){
                    $scope.generator = generator;
                });

                $scope.export = function(generator){
                    confirm.open({
                        title:"导出",
                        message:"确定导出【" + generator.name + "】？",
                        confirm:function(){
                            generatorApi.export({id: generator.id});
                        }
                    });
                };
            }
        ]);
    }
);
