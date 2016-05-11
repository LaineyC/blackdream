define(
    ["business/module", "business/api"],
    function (module) {
    "use strict";

        module.controller("statisticController", [
            "$scope", "systemApi", "viewPage",
            function($scope, systemApi, viewPage){
                viewPage.setViewPageTitle("数据统计");

                systemApi.statistic({}).success(function(result){
                    $scope.result = result;
                });
            }
        ]);

        module.controller("statisticUserController", [
            "$scope", "userApi",
            function($scope, userApi){
                $scope.searchRequest = {page:1, pageSize:10};

                $scope.isDisableOptions = [
                    {isDisable:false,name:"激活"},
                    {isDisable:true,name:"冻结"}
                ];

                $scope.isDeveloperOptions = [
                    {isDeveloper:false,name:"使用者"},
                    {isDeveloper:true,name:"开发者"}
                ];

                $scope.search = function(){
                    userApi.search($scope.searchRequest).success(function(pagerResult){
                        $scope.pagerResult = pagerResult;
                    });
                };

                $scope.search();
            }
        ]);

        module.controller("statisticGeneratorInstanceController", [
            "$scope", "generatorInstanceApi",
            function($scope, generatorInstanceApi){
                $scope.searchRequest = {page:1, pageSize:10};

                $scope.search = function(){
                    generatorInstanceApi.search($scope.searchRequest).success(function(pagerResult){
                        $scope.pagerResult = pagerResult;
                    });
                };
                $scope.search();
            }
        ]);

        module.controller("statisticGeneratorController", [
            "$scope", "generatorApi",
            function($scope, generatorApi){
                $scope.searchRequest = {page:1, pageSize:10};

                $scope.statuses = [
                    {isOpen:true,name:"公开"},
                    {isOpen:false,name:"私有"}
                ];

                $scope.search = function(){
                    generatorApi.search($scope.searchRequest).success(function(pagerResult){
                        $scope.pagerResult = pagerResult;
                    });
                };
                $scope.search();
            }
        ]);

        module.controller("statisticDynamicModelController", [
            "$scope", "dynamicModelApi",
            function($scope, dynamicModelApi){
                $scope.searchRequest = {page:1, pageSize:10};

                $scope.search = function(){
                    dynamicModelApi.search($scope.searchRequest).success(function(pagerResult){
                        $scope.pagerResult = pagerResult;
                    });
                };

                $scope.search()
            }
        ]);

        module.controller("statisticTemplateController", [
            "$scope", "templateApi",
            function($scope, templateApi){
                $scope.searchRequest = {page:1, pageSize:10};

                $scope.search = function(){
                    templateApi.search($scope.searchRequest).success(function(pagerResult){
                        $scope.pagerResult = pagerResult;
                    });
                };

                $scope.search()
            }
        ]);

        module.controller("statisticTemplateStrategyController", [
            "$scope", "templateStrategyApi",
            function($scope, templateStrategyApi){
                $scope.searchRequest = {page:1, pageSize:10};

                $scope.search = function(){
                    templateStrategyApi.search($scope.searchRequest).success(function(pagerResult){
                        $scope.pagerResult = pagerResult;
                    });
                };

                $scope.search();
            }
        ]);

    }
);