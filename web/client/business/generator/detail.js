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

                $scope.open = function(generator){
                    if(generator.isOpen){
                        return;
                    }
                    confirm.open({
                        title: "发布",
                        message:"确定发布【" + generator.name +"】？",
                        confirm:function(){
                            generatorApi.open({id:generator.id,isOpen:true}).success(function(){
                                generator.isOpen = true;
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
