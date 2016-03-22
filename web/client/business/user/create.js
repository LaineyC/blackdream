define(
    ["business/module", "business/api"],
    function (module) {
        "use strict";

        module.controller("userCreateController", [
            "$scope", "alert", "location", "userApi","viewPage",
            function($scope, alert, location, userApi, viewPage){
                viewPage.setViewPageTitle("用户新建");

                $scope.createRequest = {isDisable : false};

                $scope.create = function(){
                    userApi.create($scope.createRequest).success(function(){
                        location.go("/business/user/manage");
                    });
                };

                $scope.validateMessages = {
                    userName:{
                        required:"必输项",
                        pattern:"4-20位数字或字母"
                    }
                };

                $scope.getMessage = function(field, $error, validateMessages){
                    for(var k in $error)
                        return validateMessages[field][k];
                };

            }
        ]);
    }
);
