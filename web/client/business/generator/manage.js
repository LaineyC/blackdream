define(
    ["business/module", "business/api"],
    function (module) {
        "use strict";

        module.controller("generatorManageController", [
            "$scope", "generatorApi", "viewPage", "confirm",
            function($scope, generatorApi, viewPage, confirm){
                viewPage.setViewPageTitle("生成器管理");
                $scope.searchRequest = {page:1, pageSize:10, sortField:"instanceCount", sortDirection:"DESC"};

                $scope.statuses = [
                    {isOpen:true,name:"发布"},
                    {isOpen:false,name:"维护"}
                ];

                $scope.search = function(searchRequest){
                    if(searchRequest){
                        for(var k in searchRequest){
                            $scope.searchRequest[k] = searchRequest[k];
                        }
                    }
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
                    if(generator.isApplied){
                        return;
                    }
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

                $scope.search();
            }
        ]);
    }
);
