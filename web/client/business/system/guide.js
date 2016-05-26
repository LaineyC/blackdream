define(
    ["business/module", "business/api"],
    function (module) {
        "use strict";

        module.controller("guideController", [
            "$scope", "$location","$anchorScroll", "viewPage",
            function($scope, $location, $anchorScroll, viewPage){
                viewPage.setViewPageTitle("用户指南");

                $scope.anchorScroll = function(anchor){
                    $location.hash(anchor);
                    $anchorScroll.yOffset = 65;
                }

            }
        ]);
    }
);