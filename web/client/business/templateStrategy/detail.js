define(
    ["business/module", "business/api"],
    function (module) {
        "use strict";

        module.controller("templateStrategyDetailController", [
            "$scope", "$routeParams", "location", "templateStrategyApi", "templateApi", "viewPage",
            function($scope, $routeParams, location, templateStrategyApi, templateApi, viewPage){
                viewPage.setViewPageTitle("生成策略详情");
                $scope.templateStrategy = {};

                var id = $routeParams.id;
                templateStrategyApi.get({id: id}).success(function(templateStrategy){
                    templateApi.query({generatorId: templateStrategy.generator.id}).success(function(templates){
                        $scope.templates = templates;
                        angular.extend($scope.templateStrategy, templateStrategy);
                    });
                });

                $scope.formatTag = function(tag){
                    if(tag.tagName == "File"){
                        for(var i = 0 ; i < $scope.templates.length ; i++){
                            var template = $scope.templates[i];
                            if(template.id == tag.template.id){
                                tag.template = template.name;
                                return;
                            }
                        }
                    }
                    else if(tag.tagName == "Call" || tag.tagName == "Function"){
                        var argumentList = tag.arguments;
                        if(argumentList){
                            for(var i = 1 ; i <= argumentList.length ; i++){
                                tag["argument" + i] = argumentList[i-1];
                            }
                        }
                        delete tag.arguments;
                    }
                };

                $scope.tagRules = {
                    Break:{
                        children:[],
                        attributes:[]
                    },
                    Call:{
                        children:[],
                        attributes: [{name:"function",required:true},{name:"argument1"},{name:"argument2"},{name:"argument3"}]
                    },
                    Continue:{
                        children:[],
                        attributes:[]
                    },
                    File:{
                        children:["TemplateContext"],
                        attributes:[{name:"name",required:true},{name:"template",required:true}]
                    },
                    Folder:{
                        children:["Call","File","Folder","Foreach","Function","If","Set","Var"],
                        attributes:[{name:"name",required:true}]
                    },
                    Foreach:{
                        children:["Call","File","Folder","Foreach","Function","If","Set","Var"],
                        attributes:[{name:"item",required:true},{name:"items",required:true},{name:"status"}]
                    },
                    Function:{
                        children:["Call","File","Folder","Foreach","Function","If","Set","Var"],
                        attributes:[{name:"name",required:true},{name:"argument1"},{name:"argument2"},{name:"argument3"}]
                    },
                    If:{
                        children:["Call","File","Folder","Foreach","Function","If","Set","Var"],
                        attributes:[{name:"test",required:true}]
                    },
                    Return:{
                        children:[],
                        attributes:[]
                    },
                    Set:{
                        children:[],
                        attributes:[{name:"var",required:true},{name:"value",required:true}]
                    },
                    TemplateContext:{
                        children:[],
                        attributes:[{name:"var",required:true},{name:"value",required:true}]
                    },
                    TemplateStrategy:{
                        children:["Call","File","Folder","Foreach","Function","If","Set","Var"]
                    },
                    Var:{
                        children:[],
                        attributes:[{name:"name",required:true},{name:"value",required:true}]
                    }
                };

            }
        ]);
    }
);
