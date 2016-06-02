define(
    ["angular", "angular-locale", "bootstrap", "angular-bootstrap", "angular-touch", "angular-sanitize", "angular-animate", "angular-cookies", "angular-route", "angular-file-upload", "ui-sortable", "business/api"],
    function(angular){
        "use strict";

        //加载依赖方法
        function loadDependencies(dependencies){
            if(!dependencies){
                return dependencies;
            }
            var deferred,
                isLoad,
                definition = {
                    "load": [
                        "$q", "$rootScope",
                        function($q, $rootScope){
                            deferred = $q.defer();
                            if(!isLoad){
                                require(dependencies, function(){
                                    $rootScope.$apply(function(){
                                        deferred.resolve();
                                    });
                                });
                                isLoad = !isLoad;
                            }
                            else{
                                deferred.resolve();
                            }
                            return deferred.promise;
                        }
                    ]
                };
            return definition;
        }
        //框架总模块
        var framework = angular.module("framework", [
            "ngRoute", "ngTouch", "ngAnimate", "ngCookies", "ui.bootstrap", "ngFileUpload", "ngSanitize" ,"ui.sortable"
        ]);
        //因为注入都是单例的 缓存provider引用后 后续可以用
        var providers = {};
        framework.config([
            "$routeProvider", "$locationProvider", "$controllerProvider", "$compileProvider", "$filterProvider", "$provide", "$httpProvider",
            function($routeProvider, $locationProvider, $controllerProvider, $compileProvider, $filterProvider, $provide){
                providers.route      = $routeProvider;
                providers.location   = $locationProvider;
                providers.controller = $controllerProvider;
                providers.compile    = $compileProvider;
                providers.filter     = $filterProvider;
                providers.provider   = $provide;
            }
        ]);
        //加载模板的基础路径
        var baseUrl = "/client/";
        var routeConfig = {
            defaultRoute: "/404",
            routes: [
                // path：模板逻辑路径
                // templateUrl：模板物理真实路径
                // dependencies：每当路由器通过逻辑路径映射真实路径并且加载模板的时候，每个模本需要相应的依赖，如：angularjs的控制器，一些相关的插件（有些插件仅在某些业务才学要 文件上传等）
                // permission：加载这个模板时需要的权限
                {path: "/404", templateUrl: "framework/404.html", dependencies: null, permission: null},
                {path: "/401", templateUrl: "framework/401.html", dependencies: null, permission: null}
            ]
        };
        //设置框架默认路由
        framework.config(["configProvider", function(configProvider){
            configProvider.setRouteConfig(framework, routeConfig);
        }]);
        //http拦截器
        framework.config([
            "$provide", "$httpProvider",
            function($provide, $httpProvider){
                //加载进度条拦截器
                $provide.factory("loadingInterceptor", [
                    "$q", "loadStatus", "tooltip",
                    function($q, loadStatus, tooltip){
                        return {
                            "request": function(config) {
                                if(config.url != "/api?method=session.heartbeat"){
                                    loadStatus.run();
                                }
                                return config;
                            },
                            "requestError": function(rejection) {
                                tooltip.open({message:"loading拦截器请求错误",level:"warning"});
                                return $q.reject(rejection);
                            },
                            "response": function(response) {
                                if(response.config.url != "/api?method=session.heartbeat"){
                                    loadStatus.stop();
                                }
                                return response;
                            },
                            "responseError": function(response) {
                                tooltip.open({message:"loading拦截器响应错误",level:"danger"});
                                return $q.reject(response);
                            }
                        };
                    }
                ]);
                //服务端错误处理拦截器
                $provide.factory("errorInterceptor", [
                    "$q", "tooltip", "http", "location",
                    function($q, tooltip, http, location){
                        return {
                            "request": function(config) {
                                return config;
                            },
                            "requestError": function(rejection) {
                                tooltip.open({message:"error拦截器请求错误",level:"warning"});
                                return $q.reject(rejection);
                            },
                            "response": function(response) {
                                var error = http.parseError(response);
                                if(error){
                                    if(error.code == "401"){
                                        location.go("/401");
                                        return;
                                    }
                                    tooltip.open({message:error.message,level:"danger"});
                                    return $q.reject(error);
                                }
                                var body = http.parseBody(response);
                                if(!(body == null ||  body == undefined)){
                                    response.data = body;
                                }
                                return response;
                            },
                            "responseError": function(response) {
                                tooltip.open({message:"error拦截器响应错误",level:"danger"});
                                return $q.reject(response);
                            }
                        };
                    }
                ]);
                $httpProvider.interceptors.push("errorInterceptor");
                $httpProvider.interceptors.push("loadingInterceptor");
            }
        ]);
        //配置服务
        framework.provider("config", [
            function() {
                var routeConfigs = {};
                return {
                    setRouteConfig: function(module, routeConfig) {
                        angular.forEach(routeConfig.routes, function (config, index) {
                            if(!config.permission) {
                                providers.route.when(config.path, {
                                    template: config.template,
                                    templateUrl: baseUrl + config.templateUrl + "?_v=" + window.BLACKDREAM_VERSION,
                                    resolve: loadDependencies(config.dependencies)
                                });
                            }
                        });
                        if(routeConfig.defaultRoute) {
                            providers.route.otherwise({redirectTo: routeConfig.defaultRoute});
                        }
                        routeConfigs[module.name] = routeConfig;
                        module.controller = providers.controller.register;
                        module.directive  = providers.compile.directive;
                        module.filter     = providers.filter.register;
                        module.factory    = providers.provider.factory;
                        module.service    = providers.provider.service;
                        module.provider   = providers.provider.provider;
                    },
                    $get:[function() {
                        return {
                            getRouteConfigs: function(){return routeConfigs;}
                        };
                    }]
                };
            }
        ]);
        //框架安全服务
        framework.factory("security",[
            function(){
                var provider = {},
                    user;
                //设置用户信息信息
                provider.setUser = function(_user){
                    user = _user;
                };
                //获取用户信息信息
                provider.getUser = function(){
                    return user;
                };
                return provider;
            }
        ]);
        //当前页面
        framework.factory("viewPage",[
            function(){
                var provider = {},
                    viewPage = {};
                //设置用户信息信息
                provider.setViewPageTitle = function(title){
                    viewPage.title = title;
                };
                //获取用户信息信息
                provider.getViewPage = function(){
                    return viewPage;
                };
                return provider;
            }
        ]);
        //粘贴板
        framework.factory("clipboard",[
            function(){
                var provider = {},
                    clipboard = {};
                //
                provider.get = function(sourceType){
                    return clipboard[sourceType];
                };
                //
                provider.copy = function(sourceType, value){
                    clipboard[sourceType] = value;
                };
                return provider;
            }
        ]);
        //http响应解析服务
        framework.factory("http", [
            function() {
                var provider = {};
                /**与服务端接口对接
                *  {
                *      "error":{message:,code:,level},
                *      "body":{}
                *  }
                */
                provider.parseBody = function(response){
                    return response.data.body;
                };

                provider.parseError = function(response){
                    var error = response.data.error;
                    if(!error){
                        return null;
                    }
                    return error;
                };
                return provider;
            }
        ]);
        //base64
        framework.factory("base64",[
            function(){
                var provider = {
                    // 转码表
                    table : [
                        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
                        'I', 'J', 'K', 'L', 'M', 'N', 'O' ,'P',
                        'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
                        'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',
                        'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
                        'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
                        'w', 'x', 'y', 'z', '0', '1', '2', '3',
                        '4', '5', '6', '7', '8', '9', '+', '/'
                    ],
                    UTF16ToUTF8 : function(str) {
                        var res = [], len = str.length;
                        for (var i = 0; i < len; i++) {
                            var code = str.charCodeAt(i);
                            if (code > 0x0000 && code <= 0x007F) {
                                // 单字节，这里并不考虑0x0000，因为它是空字节
                                // U+00000000 – U+0000007F  0xxxxxxx
                                res.push(str.charAt(i));
                            }
                            else if (code >= 0x0080 && code <= 0x07FF) {
                                // 双字节
                                // U+00000080 – U+000007FF  110xxxxx 10xxxxxx
                                // 110xxxxx
                                var byte1 = 0xC0 | ((code >> 6) & 0x1F);
                                // 10xxxxxx
                                var byte2 = 0x80 | (code & 0x3F);
                                res.push(
                                    String.fromCharCode(byte1),
                                    String.fromCharCode(byte2)
                                );
                            }
                            else if (code >= 0x0800 && code <= 0xFFFF) {
                                var byte1 = 0xE0 | ((code >> 12) & 0x0F);
                                var byte2 = 0x80 | ((code >> 6) & 0x3F);
                                var byte3 = 0x80 | (code & 0x3F);
                                res.push(
                                    String.fromCharCode(byte1),
                                    String.fromCharCode(byte2),
                                    String.fromCharCode(byte3)
                                );
                            }
                            else if (code >= 0x00010000 && code <= 0x001FFFFF) {
                                // 四字节
                                // U+00010000 – U+001FFFFF  11110xxx 10xxxxxx 10xxxxxx 10xxxxxx
                            }
                            else if (code >= 0x00200000 && code <= 0x03FFFFFF) {
                                // 五字节
                                // U+00200000 – U+03FFFFFF  111110xx 10xxxxxx 10xxxxxx 10xxxxxx 10xxxxxx
                            }
                            else /** if (code >= 0x04000000 && code <= 0x7FFFFFFF)*/ {
                                // 六字节
                                // U+04000000 – U+7FFFFFFF  1111110x 10xxxxxx 10xxxxxx 10xxxxxx 10xxxxxx 10xxxxxx
                            }
                        }

                        return res.join('');
                    },
                    UTF8ToUTF16 : function(str) {
                        var res = [], len = str.length;
                        var i = 0;
                        for (var i = 0; i < len; i++) {
                            var code = str.charCodeAt(i);
                            // 对第一个字节进行判断
                            if (((code >> 7) & 0xFF) == 0x0) {
                                // 单字节
                                // 0xxxxxxx
                                res.push(str.charAt(i));
                            }
                            else if (((code >> 5) & 0xFF) == 0x6) {
                                // 双字节
                                // 110xxxxx 10xxxxxx
                                var code2 = str.charCodeAt(++i);
                                var byte1 = (code & 0x1F) << 6;
                                var byte2 = code2 & 0x3F;
                                var utf16 = byte1 | byte2;
                                res.push(Sting.fromCharCode(utf16));
                            }
                            else if (((code >> 4) & 0xFF) == 0xE) {
                                // 三字节
                                // 1110xxxx 10xxxxxx 10xxxxxx
                                var code2 = str.charCodeAt(++i);
                                var code3 = str.charCodeAt(++i);
                                var byte1 = (code << 4) | ((code2 >> 2) & 0x0F);
                                var byte2 = ((code2 & 0x03) << 6) | (code3 & 0x3F);
                                var utf16 = ((byte1 & 0x00FF) << 8) | byte2
                                res.push(String.fromCharCode(utf16));
                            }
                            else if (((code >> 3) & 0xFF) == 0x1E) {
                                // 四字节
                                // 11110xxx 10xxxxxx 10xxxxxx 10xxxxxx
                            }
                            else if (((code >> 2) & 0xFF) == 0x3E) {
                                // 五字节
                                // 111110xx 10xxxxxx 10xxxxxx 10xxxxxx 10xxxxxx
                            }
                            else /** if (((code >> 1) & 0xFF) == 0x7E)*/ {
                                // 六字节
                                // 1111110x 10xxxxxx 10xxxxxx 10xxxxxx 10xxxxxx 10xxxxxx
                            }
                        }

                        return res.join('');
                    },
                    encode : function(str) {
                        if (!str) {
                            return '';
                        }
                        var utf8    = this.UTF16ToUTF8(str); // 转成UTF8
                        var i = 0; // 遍历索引
                        var len = utf8.length;
                        var res = [];
                        while (i < len) {
                            var c1 = utf8.charCodeAt(i++) & 0xFF;
                            res.push(this.table[c1 >> 2]);
                            // 需要补2个=
                            if (i == len) {
                                res.push(this.table[(c1 & 0x3) << 4]);
                                res.push('==');
                                break;
                            }
                            var c2 = utf8.charCodeAt(i++);
                            // 需要补1个=
                            if (i == len) {
                                res.push(this.table[((c1 & 0x3) << 4) | ((c2 >> 4) & 0x0F)]);
                                res.push(this.table[(c2 & 0x0F) << 2]);
                                res.push('=');
                                break;
                            }
                            var c3 = utf8.charCodeAt(i++);
                            res.push(this.table[((c1 & 0x3) << 4) | ((c2 >> 4) & 0x0F)]);
                            res.push(this.table[((c2 & 0x0F) << 2) | ((c3 & 0xC0) >> 6)]);
                            res.push(this.table[c3 & 0x3F]);
                        }

                        return res.join('');
                    },
                    decode : function(str) {
                        if (!str) {
                            return '';
                        }

                        var len = str.length;
                        var i   = 0;
                        var res = [];

                        while (i < len) {
                            var code1 = this.table.indexOf(str.charAt(i++));
                            var code2 = this.table.indexOf(str.charAt(i++));
                            var code3 = this.table.indexOf(str.charAt(i++));
                            var code4 = this.table.indexOf(str.charAt(i++));

                            var c1 = (code1 << 2) | (code2 >> 4);
                            var c2 = ((code2 & 0xF) << 4) | (code3 >> 2);
                            var c3 = ((code3 & 0x3) << 6) | code4;

                            res.push(String.fromCharCode(c1));

                            if (code3 != 64) {
                                res.push(String.fromCharCode(c2));
                            }
                            if (code4 != 64) {
                                res.push(String.fromCharCode(c3));
                            }

                        }

                        return this.UTF8ToUTF16(res.join(''));
                    }
                };
                return provider;
            }
        ]);
        //路径跳转服务
        framework.factory("location",[
            "$window", "$location",
            function($window, $location) {
                var provider = {},
                    status = {animate:"go"};
                provider.go = function(path){
                    status.animate = "go";
                    if(path){
                        $location.path(path);
                    }
                    else{
                        $window.history.go(1);
                    }
                    //$($window.document.body).scrollTop(0);
                };
                provider.back = function(path){
                    status.animate = "back";
                    if(path){
                        $location.path(path);
                    }
                    else{
                        $window.history.back();
                    }
                    //$($window.document.body).scrollTop(0);
                };
                provider.status = function(){
                    return status;
                };
                return provider
            }
        ]);
        //页面跳转go指令
        framework.directive("locationGo", [
            "location",
            function(location) {
                return {
                    restrict: "A",
                    link : function(scope, $element, attrs) {
                        var path = attrs.locationGo;
                        attrs.$observe("locationGo",function(newValue){
                            path = newValue;
                        });
                        $element.bind("click", function(){
                            scope.$apply(function () {
                                location.go(path);
                            });
                        })
                    }
                };
            }
        ]);
        //ace指令
        framework.directive("aceEditor", ['$timeout', function ($timeout) {
            var resizeEditor = function (editor, elem) {
                /*
                var lineHeight = editor.renderer.lineHeight;
                var rows = editor.getSession().getLength();
                $(elem).height(rows * lineHeight);
                editor.resize();
                */
            };
            return {
                restrict: 'A',
                require: '?ngModel',
                scope: {
                    aceTheme:"=",
                    aceFontSize:"="
                },
                link: function (scope, elem, attrs, ngModel) {
                    var node = elem[0];

                    var editor = ace.edit(node);
                    node.style.fontSize = scope.aceFontSize || "12px";
                    scope.$watch("aceFontSize", function(newValue, oldValue){
                        node.style.fontSize = scope.aceFontSize;
                    });
                    editor.setTheme('ace/theme/' + scope.aceTheme);
                    scope.$watch("aceTheme", function(newValue, oldValue){
                        editor.setTheme('ace/theme/' + newValue);
                    });
                    editor.getSession().setMode("ace/mode/velocity");
                    editor.setShowPrintMargin(false);
                    ngModel.$render = function () {
                        editor.setValue(ngModel.$viewValue);
                        resizeEditor(editor, elem);
                    };
                    editor.on('change', function () {
                        $timeout(function () {
                            scope.$apply(function () {
                                var value = editor.getValue();
                                ngModel.$setViewValue(value);
                            });
                        });
                        resizeEditor(editor, elem);
                    });
                    elem.on('$destroy', function () {
                        editor.destroy();
                        editor.container.remove();
                    });
                }
            };
        }]);
        //页面跳转back指令
        framework.directive("locationBack", [
            "location",
            function(location) {
                return {
                    restrict: "A",
                    link : function(scope, $element, attrs) {
                        var path = attrs.locationBack;
                        attrs.$observe("locationBack", function(newValue){
                            path = newValue;
                        });
                        $element.bind("click",function(){
                            scope.$apply(function () {
                                location.back(path);
                            });
                        })
                    }
                };
            }
        ]);
        //自定义验证指令
        framework.directive("validator", [
            function () {
                return{
                    restrict: 'A',
                    require: 'ngModel',
                    link: function (scope, elem, attrs, ctrl) {
                        var validateFn,
                            validator = scope.$eval(attrs.validator);
                        angular.forEach(validator, function (rule, key) {
                            validateFn = function (value) {
                                var expression = scope.$eval(rule, {"$viewValue": value});
                                if (angular.isObject(expression) && angular.isFunction(expression.then)) {
                                    expression.then(
                                        function () {
                                            ctrl.$setValidity(key, true);
                                        },
                                        function () {
                                            ctrl.$setValidity(key, false);
                                        }
                                    );
                                }
                                else if (expression) {
                                    ctrl.$setValidity(key, true);
                                }
                                else {
                                    ctrl.$setValidity(key, false);
                                }
                                return value;
                            };
                            ctrl.$formatters.push(validateFn);
                            ctrl.$parsers.push(validateFn);
                        });
                    }
                };
            }
        ]);
        //消息条服务
        framework.factory("tooltip", [
            "$timeout",
            function($timeout){
                var provider = {},
                    tooltips = [],
                    flashTooltips = [],
                    max = 100;
                function add(message, level){
                    if(!message){
                        return;
                    }
                    var messageItem = {message: message, level:level,dateTime: new Date()};
                    flashTooltips.push(messageItem);
                    tooltips.unshift(messageItem);
                    if(tooltips.length > max){
                        tooltips.length = max;
                    }
                    $timeout(function(){
                        for(var i = 0 ; i < flashTooltips.length ; i++){
                            if(flashTooltips[i] == messageItem){
                                flashTooltips.splice(i, 1);
                                return;
                            }
                        }
                    },30 * 1000);
                }

                provider.getAll = function(){
                    return tooltips;
                };

                provider.getFlash = function(){
                    return flashTooltips;
                };
                //success info warning danger
                provider.open = function(message){
                    var level = "info";
                    if(angular.isObject(message)){
                        level = message.level;
                        message = message.message;
                    }
                    add(message, level);
                };

                provider.search = function(page, pageSize){
                    var index = (page - 1) * pageSize;
                    return {
                        records: tooltips.slice(index, index + pageSize),
                        total: tooltips.length
                    }
                };
                return provider;
            }
        ]);
        //alert服务
        framework.factory("alert", [
            "$uibModal",
            function( $uibModal ){
                return {
                    open: function (message) {
                        var title, confirm;
                        if(angular.isObject(message)){
                            title = message.title;
                            confirm = message.confirm;
                            message = message.message;
                        }
                        $uibModal.open({
                            templateUrl: "framework/alert.html",
                            controller: ["$scope", "$uibModalInstance", function ($scope, $uibModalInstance) {
                                $scope.message = message;
                                $scope.title = title || "提示";
                                $scope.confirm = function () {
                                    confirm && confirm();
                                    $uibModalInstance.close();
                                }
                            }]
                        });
                    }
                };
            }
        ]);
        //confirm服务
        framework.factory("confirm", [
            "$uibModal",
            function( $uibModal ){
                return {
                    open: function (message) {
                        var title, cancel, confirm;
                        if(angular.isObject(message)){
                            title = message.title;
                            confirm = message.confirm;
                            cancel = message.cancel;
                            message = message.message;
                        }
                        $uibModal.open({
                            templateUrl: "framework/confirm.html",
                            controller: ["$scope", "$uibModalInstance", function ($scope, $uibModalInstance) {
                                $scope.message = message;
                                $scope.title = title || "确认";
                                $scope.cancel = function () {
                                    cancel && cancel();
                                    $uibModalInstance.close();
                                };
                                $scope.confirm = function () {
                                    confirm && confirm();
                                    $uibModalInstance.close();
                                };
                            }]
                        });
                    }
                };
            }
        ]);
        //加载状态服务
        framework.factory("loadStatus", [
            function() {
                var provider = {},
                    status = {
                        loading: false,
                        progress: 0
                    };
                provider.run = function(){
                    status.progress += 1;
                    status.loading = true;
                };
                provider.getStatus = function(){
                    return status;
                };
                provider.stop = function(){
                    status.progress -= 1;
                    if(status.loading && status.progress == 0){
                        status.loading = false;
                    }
                };
                return provider;
            }
        ]);
        //框架总控制器
        framework.controller("frameworkController", [
            "$scope", "$rootScope", "$cookies", "$window", "$uibModal", "$interval", "loadStatus", "tooltip", "alert", "security", "$http", "userApi", "location","viewPage", "systemApi",
            function($scope, $rootScope, $cookies, $window, $uibModal, $interval, loadStatus, tooltip, alert, security, $http, userApi, location, viewPage, systemApi) {
                //半小时会话心跳 防止session过期
                $interval(function(){systemApi.heartbeat({});}, 30 * 60 * 1000);

                //系统全局变量用 $变量名$ 方式命名
                //系统安全信息变量
                $rootScope.$security$ = {};

                userApi.authGet({}).success(function(user){
                    security.setUser(user);
                    $scope.user = user;
                    //系统用户信息
                    $rootScope.$security$.user = user;
                });

                $scope.viewPage = viewPage.getViewPage();

                $scope.loadStatus = {status: loadStatus.getStatus()};

                $scope.tooltips = tooltip.getFlash();

                $scope.keyword = "";
                $scope.search = function(){
                    if("" == $scope.keyword){
                        location.go("/business/generator/search");
                    }
                    else{
                        location.go("/business/generator/search/" + $scope.keyword);
                    }
                };

                $scope.openTooltipHistoryModal = function(){
                    $uibModal.open({
                        size: "lg",
                        templateUrl: "framework/tooltip-history.html",
                        controller: ["$scope","$uibModalInstance",function ($scope, $uibModalInstance){
                            $scope.confirm = function(){
                                $uibModalInstance.close();
                            };
                            $scope.pager = {page:1, pageSize:10};
                            $scope.search = function(){
                                $scope.result = tooltip.search($scope.pager.page, $scope.pager.pageSize);
                            };
                            $scope.search();
                        }]
                    });
                };

                $scope.openUpdatePasswordModal = function(){
                    $uibModal.open({
                        templateUrl: "framework/passwordUpdate.html",
                        controller: ["$scope","$uibModalInstance",function ($scope, $uibModalInstance){
                            $scope.request = {};

                            $scope.validateMessages = {
                                oldPassword:{
                                    required:"必输项",
                                    pattern:"6-20位字母或数字"
                                },
                                newPassword:{
                                    required:"必输项",
                                    pattern:"6-20位字母或数字",
                                    repeat:"密码不一致"
                                },
                                repeatPassword:{
                                    required:"必输项",
                                    pattern:"6-20位字母或数字",
                                    repeat:"密码不一致"
                                }
                            };

                            $scope.getMessage = function(field, $error, validateMessages){
                                if(!$error){
                                    return;
                                }
                                if(!("required" in $error)){
                                    if($error.pattern){
                                        return validateMessages[field].pattern;
                                    }
                                }
                                for(var k in $error) {
                                    return validateMessages[field][k];
                                }
                            };

                            $scope.cancel = function(){
                                $uibModalInstance.close();
                            };
                            $scope.confirm = function(){
                                userApi.passwordUpdate($scope.request).success(function(){
                                    $uibModalInstance.close();
                                });
                            };
                            $scope.repeatNewPassword = function($scope, ctrl){
                                if(ctrl.$viewValue == undefined){
                                    return true;
                                }
                                if($scope.request.repeatPassword == ctrl.$viewValue){
                                    $scope.frameworkPasswordUpdateForm.repeatPassword.$setValidity("repeat", true);
                                    return true;
                                }
                                else{
                                    return false;
                                }
                            };
                            $scope.repeatRepeatPassword = function($scope, ctrl){
                                if(ctrl.$viewValue == undefined){
                                    return true;
                                }
                                if($scope.request.newPassword == ctrl.$viewValue){
                                    $scope.frameworkPasswordUpdateForm.newPassword.$setValidity("repeat", true);
                                    return true;
                                }
                                else{
                                    return false;
                                }
                            };
                        }]
                    });
                };

                $scope.openLogoutModal = function(){
                    $uibModal.open({
                        templateUrl: "framework/logout.html",
                        controller: ["$scope","$uibModalInstance",function ($scope, $uibModalInstance){
                            $scope.cancel = function(){
                                $uibModalInstance.close();
                            };
                            $scope.confirm = function(){
                                userApi.logout({}).success(function(){
                                    $uibModalInstance.close();
                                    window.location = "/client/login.html";
                                });
                            }
                        }]
                    });
                };

                $scope.openAboutModal = function(){
                    $uibModal.open({
                        templateUrl: "framework/about.html",
                        controller: ["$scope","$uibModalInstance",function ($scope, $uibModalInstance){
                            $scope.confirm = function(){
                                $uibModalInstance.close();
                            }
                        }]
                    });
                };

                $scope.openSelectThemeModal = function(){
                    $uibModal.open({
                        templateUrl: "framework/theme.html",
                        controller: ["$scope","$uibModalInstance",function ($scope, $uibModalInstance){
                            $scope.themes = [
                                "cerulean","cosmo","cyborg","darkly","flatly","journal","lumen","paper","readable","sandstone","simplex","slate","spacelab","superhero","united","yeti"
                            ];
                            $scope.selectTheme = function(theme) {
                                if ($cookies.get("theme") != theme) {
                                    $cookies.put("theme", theme, {path: "/"});
                                    $scope.theme = theme;
                                    $window.location.reload();
                                }
                                else{
                                    $uibModalInstance.close();
                                }
                            };
                            $scope.cancel = function(){
                                $uibModalInstance.close();
                            };
                        }]
                    });
                };
            }
        ]);

        return framework;
    }
);