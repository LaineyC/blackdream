define(["business/module"],function(module){
    "use strict";

    module.factory("systemApi",[
        "$http","$window",
        function($http, $window){
            var provider = {};

            provider.heartbeat = function(request) { return $http.post("/api?method=session.heartbeat", request); };

            provider.download = function(request) {
                return $http.post("/api?method=file.download", request, {responseType :"blob"}).success(function(data, status, headers){
                    headers = headers();
                    var document = $window.document;
                    var aLink = document.createElement("a");
                    var blob = new Blob([data]);
                    var evt = document.createEvent("HTMLEvents");
                    evt.initEvent("click", false, false);
                    aLink.download = decodeURI(headers["filename"]);
                    aLink.href = URL.createObjectURL(blob);
                    aLink.dispatchEvent(evt);
                });
            };
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

            provider.search = function(request) { return $http.post("/api?method=user.search", request); };

            //provider.login = function(request) { return $http.post("/api?method=user.login", request); };

            provider.logout = function(request) { return $http.post("/api?method=user.logout", request); };

            provider.passwordUpdate = function(request) { return $http.post("/api?method=user.password.update", request); };

            return provider;
        }
    ]);

    module.factory("generatorApi",[
        "$http", "$window",
        function($http, $window){
            var provider = {};

            provider.create = function(request) { return $http.post("/api?method=generator.create", request); };

            provider.delete = function(request) { return $http.post("/api?method=generator.delete", request); };

            provider.update = function(request) { return $http.post("/api?method=generator.update", request); };

            provider.get = function(request) { return $http.post("/api?method=generator.get", request); };

            provider.search = function(request) { return $http.post("/api?method=generator.search", request); };

            provider.export = function(request) {
                return $http.post("/api?method=generator.export", request, {responseType :"blob"}).success(function(data, status, headers){
                    headers = headers();
                    var document = $window.document;
                    var aLink = document.createElement("a");
                    var blob = new Blob([data]);
                    var evt = document.createEvent("HTMLEvents");
                    evt.initEvent("click", false, false);
                    aLink.download = decodeURI(headers["filename"]);
                    aLink.href = URL.createObjectURL(blob);
                    aLink.dispatchEvent(evt);
                });
            };

            provider.import = function(request) { return $http.post("/api?method=generator.import", request); };

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

            provider.run = function(request) { return $http.post("/api?method=generatorInstance.run", request); };

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

            return provider;
        }
    ]);

    return module;
});