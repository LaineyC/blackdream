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

                $scope.openOrClose = function(generator){
                    confirm.open({
                        title: generator.isOpen ? "维护" : "发布",
                        message:"确定" + (generator.isOpen ? "维护" : "发布") + "【" + generator.name +"】？",
                        confirm:function(){
                            generatorApi.openOrClose({id:generator.id, isOpen:!generator.isOpen}).success(function(_generator){
                                generator.isOpen = _generator.isOpen;
                            });
                        }
                    });
                };

                $scope.delete = function(generator){
                    if(generator.isApplied){
                        return;
                    }
                    confirm.open({
                        title:"删除",
                        message:"确定删除【" + generator.name +"】？",
                        confirm:function(){
                            generatorApi.delete({id:generator.id}).success(function(){
                                location.go("/business/generator/manage");
                            });
                        }
                    });
                };
            }
        ]);
    }
);
