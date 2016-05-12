define(
    ["business/module", "business/api"], function (module) {
        "use strict";

        module.controller("generatorManageController", [
            "$scope", "generatorApi", "viewPage", "confirm",
            function($scope, generatorApi, viewPage, confirm){
                viewPage.setViewPageTitle("生成器管理");
                $scope.searchRequest = {page:1, pageSize:10};

                $scope.statuses = [
                    {isOpen:true,name:"发布"},
                    {isOpen:false,name:"调试"}
                ];

                $scope.search = function(){
                    generatorApi.authSearch($scope.searchRequest).success(function(pagerResult){
                        $scope.pagerResult = pagerResult;
                    });
                };

                $scope.import = function($file){
                    if(!$file){
                        return;
                    }
                    var fileReader = new FileReader();
                    fileReader.onload = function(event) {
                        var result = event.target.result;
                        generatorApi.import({
                            generatorFile:{
                                name:$file.name,
                                content:result.substring(result.indexOf(",") + 1)
                            }
                        }).success(function(){
                            $scope.search();
                        });
                    };
                    fileReader.readAsDataURL($file);
                };

                $scope.export = function(generator){
                    confirm.open({
                        title:"导出",
                        message:"确定导出【" + generator.name + "】？",
                        confirm:function(){
                            generatorApi.export({id: generator.id});
                        }
                    });
                };

                $scope.delete = function(generator){
                    confirm.open({
                        title:"删除",
                        message:"确定删除【" + generator.name +"】？",
                        confirm:function(){
                            generatorApi.delete({id:generator.id}).success(function(){
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
