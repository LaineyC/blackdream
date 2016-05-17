define(
    ["business/module", "business/api"], function (module) {
        "use strict";

        module.controller("generatorSearchController", [
            "$scope", "$routeParams", "generatorApi", "viewPage",
            function($scope, $routeParams, generatorApi, viewPage){
                viewPage.setViewPageTitle("生成器搜索");

                var keyword = $routeParams.keyword;
                $scope.searchRequest = {page:1, pageSize:10, keyword:keyword, isOpen:true, sortField:"modifyDate", sortDirection:"DESC"};

                $scope.search = function(){
                    generatorApi.search($scope.searchRequest).success(function(pagerResult){
                        $scope.result = pagerResult;
                        if(keyword){
                            for(var i = 0 ; i < pagerResult.records.length ; i++){
                                var generator = pagerResult.records[i];
                                generator.name = generator.name.replace(keyword,'<mark>' + keyword + '</mark>');
                                generator.developer.userName = generator.developer.userName.replace(keyword,'<mark>' + keyword + '</mark>');
                            }
                        }
                    });
                };

                $scope.search();
            }
        ]);
    }
);
