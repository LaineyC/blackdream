define(
    ["business/module", "business/api"],
    function (module) {
        "use strict";

        module.controller("templateDetailController", [
            "$scope", "$routeParams", "location", "templateApi",
            function($scope, $routeParams, location, templateApi){
                var id = $routeParams.id;
                templateApi.get({id: id}).success(function(template){
                    $scope.template = template;
                });
            }
        ]);
    }
);
