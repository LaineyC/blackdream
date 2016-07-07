define(
    ["business/module"],
    function(module){
        "use strict";

        var download = function($http, api, request, tooltip, location){
            return $http.post(api, request, {responseType :"blob"}).success(function(data, status, headers){
                headers = headers();
                if(!headers["filename"]){
                    var fileReader = new FileReader();
                    fileReader.onload = function(event){
                        var response = eval("(" + event.target.result + ")");
                        var error = response.error;
                        if(error.code == "401"){
                            location.go("/401");
                            return;
                        }
                        tooltip.open({message:error.message, level:"danger"});
                    };
                    fileReader.readAsText(data);
                    return;
                }

                var file = new Blob([data], {type: "octet/stream"});
                var a = document.createElement("a");
                document.body.appendChild(a);
                a.className = "hidden";
                a.href = URL.createObjectURL(file);
                a.download = decodeURI(headers["filename"]);
                a.click();
            });
        };

        module.factory("systemApi",[
            "$http", "tooltip", "location",
            function($http, tooltip, location){
                var provider = {};

                provider.heartbeat = function(request) { return $http.post("/api?method=session.heartbeat", request); };

                provider.statistic = function(request) { return $http.post("/api?method=data.statistic", request); };

                provider.download = function(request) { return download($http, "/api?method=file.download", request, tooltip, location); };

                provider.currentTime = function(request) { return $http.post("/api?method=date.currentTime", request); };

                return provider;
            }
        ]);

        module.factory("userApi",[
            "$http",
            function($http){
                var provider = {};

                provider.create = function(request) { return $http.post("/api?method=user.create", request); };

                provider.delete = function(request) { return $http.post("/api?method=user.delete", request); };

                provider.update = function(request) { return $http.post("/api?method=user.update", request); };

                provider.get = function(request) { return $http.post("/api?method=user.get", request); };

                provider.authGet = function(request) { return $http.post("/api?method=user.authGet", request); };

                provider.search = function(request) { return $http.post("/api?method=user.search", request); };

                //provider.login = function(request) { return $http.post("/api?method=user.login", request); };

                provider.logout = function(request) { return $http.post("/api?method=user.logout", request); };

                provider.passwordUpdate = function(request) { return $http.post("/api?method=user.password.update", request); };

                provider.passwordReset = function(request) { return $http.post("/api?method=user.password.reset", request); };

                provider.enableOrDisable = function(request) { return $http.post("/api?method=user.enableOrDisable", request); };

                return provider;
            }
        ]);

        module.factory("generatorApi",[
            "$http", "tooltip", "location",
            function($http, tooltip, location){
                var provider = {};

                provider.create = function(request) { return $http.post("/api?method=generator.create", request); };

                provider.delete = function(request) { return $http.post("/api?method=generator.delete", request); };

                provider.update = function(request) { return $http.post("/api?method=generator.update", request); };

                provider.get = function(request) { return $http.post("/api?method=generator.get", request); };

                provider.search = function(request) { return $http.post("/api?method=generator.search", request); };

                provider.authSearch = function(request) { return $http.post("/api?method=generator.authSearch", request); };

                provider.export = function(request) { return download($http, "/api?method=generator.export", request, tooltip, location); };

                provider.import = function(request) { return $http.post("/api?method=generator.import", request); };

                provider.openOrClose = function(request) { return $http.post("/api?method=generator.openOrClose", request); };

                return provider;
            }
        ]);

        module.factory("templateApi",[
            "$http",
            function($http){
                var provider = {};

                provider.create = function(request) { return $http.post("/api?method=template.create", request); };

                provider.delete = function(request) { return $http.post("/api?method=template.delete", request); };

                provider.update = function(request) { return $http.post("/api?method=template.update", request); };

                provider.get = function(request) { return $http.post("/api?method=template.get", request); };

                provider.search = function(request) { return $http.post("/api?method=template.search", request); };

                provider.query = function(request) { return $http.post("/api?method=template.query", request); };

                provider.codeGet = function(request) { return $http.post("/api?method=template.code.get", request); };

                provider.sort = function(request) { return $http.post("/api?method=template.sort", request); };

                return provider;
            }
        ]);

        module.factory("dynamicModelApi",[
            "$http",
            function($http){
                var provider = {};

                provider.create = function(request) { return $http.post("/api?method=dynamicModel.create", request); };

                provider.delete = function(request) { return $http.post("/api?method=dynamicModel.delete", request); };

                provider.update = function(request) { return $http.post("/api?method=dynamicModel.update", request); };

                provider.get = function(request) { return $http.post("/api?method=dynamicModel.get", request); };

                provider.query = function(request) { return $http.post("/api?method=dynamicModel.query", request); };

                provider.search = function(request) { return $http.post("/api?method=dynamicModel.search", request); };

                provider.sort = function(request) { return $http.post("/api?method=dynamicModel.sort", request); };

                return provider;
            }
        ]);

        module.factory("generatorInstanceApi",[
            "$http",
            function($http){
                var provider = {};

                provider.create = function(request) { return $http.post("/api?method=generatorInstance.create", request); };

                provider.delete = function(request) { return $http.post("/api?method=generatorInstance.delete", request); };

                provider.update = function(request) { return $http.post("/api?method=generatorInstance.update", request); };

                provider.get = function(request) { return $http.post("/api?method=generatorInstance.get", request); };

                provider.search = function(request) { return $http.post("/api?method=generatorInstance.search", request); };

                provider.authSearch = function(request) { return $http.post("/api?method=generatorInstance.authSearch", request); };

                provider.run = function(request) { return $http.post("/api?method=generatorInstance.run", request); };

                provider.dataDictionary = function(request) { return $http.post("/api?method=generatorInstance.dataDictionary", request); };

                provider.versionSync = function(request) { return $http.post("/api?method=generatorInstance.versionSync", request); };

                return provider;
            }
        ]);

        module.factory("templateStrategyApi",[
            "$http",
            function($http){
                var provider = {};

                provider.create = function(request) { return $http.post("/api?method=templateStrategy.create", request); };

                provider.delete = function(request) { return $http.post("/api?method=templateStrategy.delete", request); };

                provider.update = function(request) { return $http.post("/api?method=templateStrategy.update", request); };

                provider.get = function(request) { return $http.post("/api?method=templateStrategy.get", request); };

                provider.query = function(request) { return $http.post("/api?method=templateStrategy.query", request); };

                provider.search = function(request) { return $http.post("/api?method=templateStrategy.search", request); };

                provider.sort = function(request) { return $http.post("/api?method=templateStrategy.sort", request); };

                return provider;
            }
        ]);

        module.factory("dataModelApi",[
            "$http",
            function($http){
                var provider = {};

                provider.create = function(request) { return $http.post("/api?method=dataModel.create", request); };

                provider.delete = function(request) { return $http.post("/api?method=dataModel.delete", request); };

                provider.update = function(request) { return $http.post("/api?method=dataModel.update", request); };

                provider.get = function(request) { return $http.post("/api?method=dataModel.get", request); };

                provider.tree = function(request) { return $http.post("/api?method=dataModel.tree", request); };

                provider.sort = function(request) { return $http.post("/api?method=dataModel.sort", request); };

                return provider;
            }
        ]);

        return module;
    }
);