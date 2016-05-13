define(
    ["business/module", "business/api"],
    function (module) {
        "use strict";

        module.controller("userDetailController", [
            "$scope", "$routeParams", "location", "userApi","viewPage", "confirm",
            function($scope, $routeParams, location, userApi, viewPage, confirm){
                viewPage.setViewPageTitle("用户详情");
                var id = $routeParams.id;

                userApi.get({id: id}).success(function(user){
                    $scope.user = user;
                });

                $scope.passwordReset = function(user){
                    if(!user.creator){
                        return;
                    }
                    confirm.open({
                        title:"密码重置",
                        message:"确定重置【" + user.userName + "】密码？",
                        confirm:function(){
                            userApi.passwordReset({id: user.id});
                        }
                    });
                };
            }
        ]);
    }
);
