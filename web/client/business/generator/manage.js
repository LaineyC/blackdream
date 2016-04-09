define(
    ["business/module", "business/api"], function (module) {
        "use strict";

        module.controller("generatorManageController", [
            "$scope", "generatorApi", "security", "viewPage",
            function($scope, generatorApi, security, viewPage){
                viewPage.setViewPageTitle("生成器管理");
                var user = security.getUser();
                $scope.searchRequest = {page:1, pageSize:10, developerId: user.id};

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
    }
);
