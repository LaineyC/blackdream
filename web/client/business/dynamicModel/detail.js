define(
    ["business/module", "business/api"],
    function (module) {
        "use strict";

        module.controller("dynamicModelDetailController", [
            "$scope", "$routeParams", "location", "dynamicModelApi", "viewPage", "confirm",
            function($scope, $routeParams, location, dynamicModelApi, viewPage, confirm){
                viewPage.setViewPageTitle("数据模型详情");
                var id = $routeParams.id;

                dynamicModelApi.get({id: id}).success(function(dynamicModel){
                    $scope.dynamicModel = dynamicModel;

                    var tableHead = $scope.tableHead = {groupHeads:[],heads:[]};
                    for(var j = 0 ; j < $scope.dynamicModel.association.length ; j++){
                        var property = $scope.dynamicModel.association[j];
                        var group = property.group;
                        if(!group){
                            tableHead.groupHeads.push(property);
                        }
                        else{
                            var prevHead = tableHead.groupHeads[tableHead.groupHeads.length - 1];
                            if(!prevHead || group != prevHead.group){
                                tableHead.groupHeads.push({group:group, span:1});
                            }
                            if(prevHead && group == prevHead.group){
                                prevHead.span++;
                            }
                            tableHead.heads.push(property);
                        }
                    }

                });

                $scope.typeOptions = {
                    Boolean:"布尔型",
                    Long:"整数型",
                    Double:"浮点型",
                    String:"字符串型",
                    Date:"日期型",
                    Enum:"枚举型",
                    Model:"数据模型"
                };

                $scope.validatorOptions = {
                    required:"必输",
                    min:"最小",
                    max:"最大",
                    minlength:"最短",
                    maxlength:"最长",
                    pattern:"正则",
                    patternTooltip:"提示"
                };

                $scope.delete = function(dynamicModel){
                    if(dynamicModel.generator.isApplied){
                        return;
                    }
                    confirm.open({
                        title:"删除",
                        message:"确定删除【" + dynamicModel.name +"】？",
                        confirm:function(){
                            dynamicModelApi.delete({id:dynamicModel.id}).success(function(){
                                location.go("/business/dynamicModel/manage/" + dynamicModel.generator.id);
                            });
                        }
                    });
                };
            }
        ]);
    }
);
