define(
    [
        "angular"
    ],
    function(angular){
        "use strict";

        var routeConfig = {
            routes: [
                {path: "/business/user/manage",templateUrl: "business/user/manage.html",dependencies: ["business/user/manage"],permission: null},
                {path: "/business/user/create",templateUrl: "business/user/create.html",dependencies: ["business/user/create"],permission: null},
                {path: "/business/user/update/:id",templateUrl: "business/user/update.html",dependencies: ["business/user/update"],permission: null},
                {path: "/business/user/detail/:id",templateUrl: "business/user/detail.html",dependencies: ["business/user/detail"],permission: null},

                {path: "/business/generator/manage",templateUrl: "business/generator/manage.html",dependencies: ["business/generator/manage"],permission: null},
                {path: "/business/generator/create",templateUrl: "business/generator/create.html",dependencies: ["business/generator/create"],permission: null},
                {path: "/business/generator/update/:id",templateUrl: "business/generator/update.html",dependencies: ["business/generator/update"],permission: null},
                {path: "/business/generator/detail/:id",templateUrl: "business/generator/detail.html",dependencies: ["business/generator/detail"],permission: null},
                {path: "/business/generator/search/:keyword",templateUrl: "business/generator/search.html",dependencies: ["business/generator/search"],permission: null},
                {path: "/business/generator/search",templateUrl: "business/generator/search.html",dependencies: ["business/generator/search"],permission: null},

                {path: "/business/template/manage/:generatorId",templateUrl: "business/template/manage.html",dependencies: ["ace","business/template/manage"],permission: null},

                {path: "/business/dynamicModel/manage/:generatorId",templateUrl: "business/dynamicModel/manage.html",dependencies: ["business/dynamicModel/manage"],permission: null},
                {path: "/business/dynamicModel/create/:generatorId",templateUrl: "business/dynamicModel/create.html",dependencies: ["business/dynamicModel/create"],permission: null},
                {path: "/business/dynamicModel/update/:id",templateUrl: "business/dynamicModel/update.html",dependencies: ["business/dynamicModel/update"],permission: null},
                {path: "/business/dynamicModel/detail/:id",templateUrl: "business/dynamicModel/detail.html",dependencies: ["business/dynamicModel/detail"],permission: null},

                {path: "/business/generatorInstance/manage/",templateUrl: "business/generatorInstance/manage.html",dependencies: ["business/generatorInstance/manage"],permission: null},
                {path: "/business/generatorInstance/create/:generatorId",templateUrl: "business/generatorInstance/create.html",dependencies: ["business/generatorInstance/create"],permission: null},
                {path: "/business/generatorInstance/update/:id",templateUrl: "business/generatorInstance/update.html",dependencies: ["business/generatorInstance/update"],permission: null},
                {path: "/business/generatorInstance/detail/:id",templateUrl: "business/generatorInstance/detail.html",dependencies: ["business/generatorInstance/detail"],permission: null},

                {path: "/business/templateStrategy/manage/:generatorId",templateUrl: "business/templateStrategy/manage.html",dependencies: ["business/templateStrategy/manage"],permission: null},
                {path: "/business/templateStrategy/create/:generatorId",templateUrl: "business/templateStrategy/create.html",dependencies: ["business/templateStrategy/create"],permission: null},
                {path: "/business/templateStrategy/update/:id",templateUrl: "business/templateStrategy/update.html",dependencies: ["business/templateStrategy/update"],permission: null},
                {path: "/business/templateStrategy/detail/:id",templateUrl: "business/templateStrategy/detail.html",dependencies: ["business/templateStrategy/detail"],permission: null},

                {path: "/business/dataModel/manage/:generatorInstanceId",templateUrl: "business/dataModel/manage.html",dependencies: ["business/dataModel/manage"],permission: null}

            ]
        };

        var module = angular.module("business", []);
        module.config([
            "configProvider",
            function(configProvider){
                configProvider.setRouteConfig(module, routeConfig);
            }
        ]);

        return module;
    });