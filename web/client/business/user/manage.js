define(
    ["business/module", "business/api"], function (module) {
        "use strict";

        module.controller("userManageController", [
            "$scope", "$modal", "userApi", "viewPage",
            function($scope, $modal, userApi, viewPage){
                viewPage.setViewPageTitle("用户管理");

                $scope.searchRequest = {page:1, pageSize:10};

                $scope.pageSizeList = [10, 20, 50, 100];

                $scope.statuses = [
                    {isDisable:false,name:"激活"},
                    {isDisable:true,name:"冻结"}
                ];

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
    }
);
