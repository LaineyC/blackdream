define(
    ["business/module", "business/api"], function (module) {
        "use strict";

        module.controller("templateManageEditController", [
            "$scope", "$routeParams", "templateApi","base64", "$cookies", "viewPage",
            function($scope, $routeParams, templateApi, base64, $cookies, viewPage){
                viewPage.setViewPageTitle("模板编辑");
                var generatorId = $routeParams.generatorId;
                $scope.generatorId = generatorId;

                $scope.query = function(){
                    templateApi.query({generatorId:generatorId}).success(function(templates){
                        $scope.templates = templates;
                    });
                };

                $scope.query();

                $scope.aceFontSize = $cookies.get("aceFontSize") || "12px";
                $scope.selectAceFontSize = function(aceFontSize) {
                    if ($cookies.get("aceFontSize") != aceFontSize) {
                        $cookies.put("aceFontSize", aceFontSize, {path: "/"});
                        $scope.aceFontSize = aceFontSize;
                    }
                };

                $scope.aceThemes = [
                    "ambiance","chaos","chrome","clouds","clouds_midnight","cobalt","crimson_editor","dawn","dreamweaver","eclipse","github","idle_fingers","iplastic",
                    "katzenmilch","kr_theme","kuroir","merbivore","merbivore_soft","mono_industrial","monokai","pastel_on_dark","solarized_dark","solarized_light","sqlserver",
                    "terminal","textmate","tomorrow","tomorrow_night","tomorrow_night_blue","tomorrow_night_bright","tomorrow_night_eighties","twilight","vibrant_ink","xcode"];
                $scope.aceTheme = $cookies.get("aceTheme") || "github";
                $scope.selectAceTheme = function(aceTheme) {
                    if ($cookies.get("aceTheme") != aceTheme) {
                        $cookies.put("aceTheme", aceTheme, {path: "/"});
                        $scope.aceTheme = aceTheme;
                    }
                };

                $scope.dirtyData = {
                    deleteData:{},
                    updateData:{},
                    hasDirtyData:function(){
                        var k;
                        for(k in $scope.dirtyData.deleteData){
                            return true;
                        }
                        for(k in $scope.dirtyData.updateData){
                            return true;
                        }
                        return false
                    },
                    addDeleteData:function(template){
                        if(template.id > 0){
                            $scope.dirtyData.deleteData[template.id] = template;
                        }
                        delete $scope.dirtyData.updateData[template.id];
                    },
                    addUpdateData:function(template){
                        $scope.dirtyData.updateData[template.id] = template;
                    },
                    saveDirty: function(){
                        var data = $scope.dirtyData;
                        var k;
                        for(k in data.updateData){
                            (function(){
                                var template = data.updateData[k];
                                templateApi.update({
                                    id:template.id,
                                    name:template.name,
                                    templateFile:{
                                        content:base64.encode(template.code)
                                    }
                                }).success(function(){
                                    delete data.updateData[template.id];
                                    if(template.$scope && template.$scope.templateUpdateForm){
                                        template.$scope.templateUpdateForm.$setPristine();
                                    }
                                });
                            })();
                        }
                        for(k in data.deleteData){
                            (function(){
                                var template = data.deleteData[k];
                                templateApi.delete({
                                    id:template.id
                                }).success(function(){
                                    delete data.deleteData[template.id];
                                });
                            })();
                        }
                    }
                };
                $scope.templateControl = {
                    index: new Date().getTime(),
                    add: function(){
                        var id = ($scope.templateControl.index++).toString(32);
                        var template = {id: 0 - id, name:"新建模板" + "_" + id,code:"hello blackdream",$view:true};
                        templateApi.create({
                            generatorId:generatorId,
                            name:template.name,
                            templateFile:{content:"aGVsbG8gYmxhY2tkcmVhbQ=="} //hello blackdream
                        }).success(function(t){
                            template.id = t.id;
                            if(template.$scope && template.$scope.templateUpdateForm){
                                template.$scope.templateUpdateForm.$setPristine();
                            }
                            $scope.tabsControl.add(template);
                            $scope.templates.splice(0,0,template);
                        });
                    },
                    view:function(template){
                        if(template.$view || $scope.tabsControl.contains(template)){
                            $scope.tabsControl.add(template);
                        }
                        else{
                            templateApi.codeGet({id:template.id}).success(function(code){
                                template.code = code;
                                template.$view = true;
                                $scope.tabsControl.add(template);
                            });
                        }
                    },
                    "delete":function(template,$index){
                        $scope.templates.splice($index, 1);
                        $scope.dirtyData.addDeleteData(template);
                        $scope.tabsControl.remove(template);
                    }
                };

                $scope.tabsControl = {
                    data:[],
                    add:function(template){
                        $scope.tabsControl.remove(template);
                        template.active = true;
                        $scope.tabsControl.data.splice(0, 0, template);
                        if($scope.tabsControl.data.length > 20){
                            $scope.tabsControl.data.length = 20;
                        }
                    },
                    contains:function(template){
                        var data = $scope.tabsControl.data;
                        for(var i = 0 ; i < data.length ; i++){
                            if(template == data[i]){
                                return true;
                            }
                        }
                        return false;
                    },
                    remove:function(template){
                        var data = $scope.tabsControl.data;
                        for(var i = 0 ; i < data.length ; i++){
                            if(template == data[i]){
                                data.splice(i, 1);
                                break;
                            }
                        }
                    },
                    initScope:function(template, $scope){
                        template.$scope = $scope;
                        $scope.$watch("templateUpdateForm.$dirty",function(newValue, oldValue){
                            if(newValue){
                                $scope.dirtyData.addUpdateData(template);
                            }
                        });
                    }
                };

                $scope.validateMessages = {
                    name:{
                        required:"必输项",
                        maxlength:"最长20位"
                    }
                };

                $scope.getMessage = function(field, $error, validateMessages){
                    for(var k in $error)
                        return validateMessages[field][k];
                };
            }
        ]);
    }
);
