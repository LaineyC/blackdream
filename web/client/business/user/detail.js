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

                $scope.enableOrDisable = function(user){
                    if(!user.creator){
                        return;
                    }
                    confirm.open({
                        title: user.isDisable ? "激活" : "冻结",
                        message:"确定" + (user.isDisable ? "激活" : "冻结") + "【" + user.userName +"】？",
                        confirm:function(){
                            userApi.enableOrDisable({id:user.id, isDisable:!user.isDisable}).success(function(_user){
                                user.isDisable = _user.isDisable;
                            });
                        }
                    });
                };

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
