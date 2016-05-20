define(
    ["business/module", "business/api"],
    function (module) {
        "use strict";

        module.controller("homeController", [
            "$scope", "$q", "generatorApi", "generatorInstanceApi", "$uibModal", "viewPage", "systemApi",
            function($scope, $q,  generatorApi, generatorInstanceApi, $uibModal, viewPage, systemApi){
                viewPage.setViewPageTitle("首页");

                var q1 = systemApi.currentTime({}).success(function(currentTime){
                    $scope.currentTime = currentTime;
                });

                var q2 = generatorInstanceApi.authSearch({page:1, pageSize:10, sortField:"modifyDate", sortDirection:"DESC"}).success(function(pagerResult){
                    $scope.generatorInstances = pagerResult.records;
                });

                generatorApi.authSearch({page:1, pageSize:10, sortField:"instanceCount", sortDirection:"DESC"}).success(function(pagerResult){
                    $scope.generators = pagerResult.records;
                });

                $q.all([q1, q2]).then(function(){
                    for(var i = 0 ; i < $scope.generatorInstances.length ; i++){
                        var generatorInstance = $scope.generatorInstances[i];
                        var timeDiff = $scope.currentTime - generatorInstance.modifyDate;
                        if(timeDiff < 60 * 1000){
                            generatorInstance.modifyDateTip = "刚刚";
                        }
                        else if(timeDiff < 60 * 60 * 1000){
                            generatorInstance.modifyDateTip = parseInt(timeDiff/(60 * 1000)) + "分钟前";
                        }
                        else if(timeDiff < 24 * 60 * 60 * 1000){
                            generatorInstance.modifyDateTip = parseInt(timeDiff/(60 * 60 * 1000)) + "小时前";
                        }
                        else if(timeDiff < 30 * 24 * 60 * 60 * 1000){
                            generatorInstance.modifyDateTip = parseInt(timeDiff/(24 * 60 * 60 * 1000)) + "天前";
                        }
                        else if(timeDiff < 12 * 30 * 24 * 60 * 60 * 1000){
                            generatorInstance.modifyDateTip = parseInt(timeDiff/(30 * 24 * 60 * 60 * 1000)) + "个月前";
                        }
                        else{
                            generatorInstance.modifyDateTip = "N久以前";
                        }
                    }
                });

            }
        ]);
    }
);