define(
    [
        "framework/framework"
    ],
    function (module) {
    "use strict";
        module.controller("homeController", [
            "$scope", "generatorApi", "generatorInstanceApi", "security", "$uibModal", "viewPage", "userApi",
            function($scope, generatorApi, generatorInstanceApi, security, $uibModal, viewPage, userApi){
                viewPage.setViewPageTitle("首页");

                generatorInstanceApi.search({page:1, pageSize:10}).success(function(pagerResult){
                    $scope.generatorInstances = pagerResult.records;
                });

                var user = security.getUser();
                if(user){
                    generatorApi.search({page:1, pageSize:10,developerId: user.id}).success(function(pagerResult){
                        $scope.generators = pagerResult.records;
                    });
                }
                else{
                    userApi.get({}).success(function(user){
                        generatorApi.search({page:1, pageSize:10,developerId: user.id}).success(function(pagerResult){
                            $scope.generators = pagerResult.records;
                        });
                    });
                }
            }
    ]);
});