define(
    [
        "framework/framework"
    ],
    function (module) {
    "use strict";
        module.controller("guideController", [
            "$scope", "$location","$anchorScroll",
            function($scope, $location, $anchorScroll){

                $scope.anchorScroll = function(anchor){
                    $location.hash(anchor);
                    $anchorScroll.yOffset = 65;
                }

            }
    ]);
});