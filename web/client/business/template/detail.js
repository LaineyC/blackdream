define(
    ["business/module", "business/api"],
    function (module) {
        "use strict";

        module.controller("templateDetailController", [
            "$scope", "$routeParams", "location", "templateApi", "viewPage",
            function($scope, $routeParams, location, templateApi, viewPage){
                viewPage.setViewPageTitle("模板详情");
                var id = $routeParams.id;
                templateApi.get({id: id}).success(function(template){
                    $scope.template = template;
                });
            }
        ]);
    }
);
