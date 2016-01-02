define(
    ["business/module", "business/api"], function (module) {
        "use strict";

        module.controller("generatorSearchController", [
            "$scope", "$routeParams", "generatorApi",
            function($scope, $routeParams, generatorApi){
                var searchText = $routeParams.searchText;
                $scope.searchRequest = {page:1, pageSize:10,name:searchText,isOpen:true};

                $scope.search = function(){
                    generatorApi.search($scope.searchRequest).success(function(pagerResult){
                        $scope.result = pagerResult;
                        if(searchText){
                            for(var i = 0 ; i < pagerResult.records.length ; i++){
                                var generator = pagerResult.records[i];
                                generator.name = generator.name.replace(searchText,'<mark>' + searchText + '</mark>');
                                generator.developer.userName = generator.developer.userName.replace(searchText,'<mark>' + searchText + '</mark>');
                            }
                        }
                    });
                };

                $scope.search();
            }
        ]);
    }
);
