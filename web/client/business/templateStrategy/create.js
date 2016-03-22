define(
    ["business/module", "business/api"],
    function (module) {
        "use strict";

        module.controller("templateStrategyCreateController", [
            "$scope", "$routeParams", "location", "templateStrategyApi", "templateApi","viewPage",
            function($scope, $routeParams, location, templateStrategyApi, templateApi, viewPage){
                viewPage.setViewPageTitle("策略文件新建");
                var generatorId = $routeParams.generatorId;

                $scope.createRequest = {generatorId: generatorId};

                templateApi.query({generatorId: generatorId}).success(function(templates){
                    $scope.templates = templates;
                });

                $scope.add = function(tagName, parent){
                    if(!parent.children) {
                        parent.children = [];
                    }
                    parent.children.push({tagName: tagName});
                };

                $scope.delete = function(child, parent){
                    for(var i = 0 ; i < parent.children.length ; i++){
                        if(child == parent.children[i]){
                            parent.children.splice(i, 1);
                            return;
                        }
                    }
                };

                $scope.sortableOptions = {
                    update: function(e, ui) {
                    },
                    stop: function(e, ui) {
                    }
                };

                $scope.getMessage = function(field, $error, validateMessages){
                    for(var k in $error) {
                        var rule = validateMessages[field][k];
                        return rule ? rule.message : null;
                    }
                };

                $scope.tagRules = {
                    Break:{
                        children:[],
                        attributes:{}
                    },
                    Call:{
                        children:[],
                        attributes: {
                            "function":{size:20,required:{rule:true,message:"必输项"}},
                            argument1:{size:20},
                            argument2:{size:20},
                            argument3:{size:20},
                            argument4:{size:20}
                        }
                    },
                    Continue:{
                        children:[],
                        attributes:{}
                    },
                    File:{
                        children:["TemplateContext"],
                        attributes:{
                            name:{size:50,required:{rule:true,message:"必输项"}},
                            template:{required:{rule:true,message:"必输项"}}
                        }
                    },
                    Folder:{
                        children:["Call","File","Folder","Foreach","Function","If","Set","Var"],
                        attributes:{
                            name:{size:50,required:{rule:true,message:"必输项"}}
                        }
                    },
                    Foreach:{
                        children:["Break","Call","Continue","File","Folder","Foreach","Function","If","Return","Set","Var"],
                        attributes:{
                            item:{required:{rule:true,message:"必输项"},size:20},
                            items:{required:{rule:true,message:"必输项"},size:20},
                            status:{size:20}
                        }
                    },
                    Function:{
                        children:["Call","File","Folder","Foreach","Function","If","Return","Set","Var"],
                        attributes:{
                            name:{required:{rule:true,message:"必输项"}},
                            argument1:{size:20},
                            argument2:{size:20},
                            argument3:{size:20},
                            argument4:{size:20}
                        }
                    },
                    If:{
                        children:["Break","Call","Continue","File","Folder","Foreach","Function","If","Return","Set","Var"],
                        attributes:{
                            test:{required:{rule:true,message:"必输项"},size:20}
                        }
                    },
                    Return:{
                        children:[],
                        attributes:{}
                    },
                    Set:{
                        children:[],
                        attributes:{
                            "var":{required:{rule:true,message:"必输项"},size:20},
                            value:{required:{rule:true,message:"必输项"},size:20}
                        }
                    },
                    TemplateContext:{
                        children:[],
                        attributes:{
                            "var":{required:{rule:true,message:"必输项"},size:20},
                            value:{required:{rule:true,message:"必输项"},size:20}
                        }
                    },
                    TemplateStrategy:{
                        children:["Call","File","Folder","Foreach","Function","If","Set","Var"],
                        attributes:{
                            name:{required:{rule:true,message:"必输项"},maxlength:{rule:20,message:"最长20位"}}
                        }
                    },
                    Var:{
                        children:[],
                        attributes:{
                            name:{required:{rule:true,message:"必输项"},size:20},
                            value:{required:{rule:true,message:"必输项"},size:20}
                        }
                    }
                };

                $scope.hashKey = function(entity){
                    return entity.$$hashKey.split(":")[1];
                };

                $scope.create = function(){
                    templateStrategyApi.create($scope.createRequest).success(function(){
                        location.go("/business/templateStrategy/manage/" + generatorId);
                    });
                };

            }
        ]);
    }
);
