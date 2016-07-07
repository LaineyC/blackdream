window.BLACKDREAM_VERSION = "1.0.0";
require.config({
    baseUrl: "/client",
    paths:{
        "jquery": "library/jquery/jquery.min",
        "angular": "library/angular/angular.min",
        "bootstrap": "library/bootstrap/js/bootstrap.min",
        "angular-bootstrap": "library/angular-bootstrap/ui-bootstrap-tpls.min",
        "angular-route": "library/angular/angular-route.min",
        "angular-touch": "library/angular/angular-touch.min",
        "angular-sanitize" : "library/angular/angular-sanitize.min",
        "angular-animate": "library/angular/angular-animate.min",
        "angular-cookies": "library/angular/angular-cookies.min",
        "angular-locale": "library/angular/i18n/angular-locale_zh",
        "angular-file-upload": "library/angular-file-upload/ng-file-upload.min",
        "ui-sortable": "library/angular-ui/ui-sortable",
        "jquery-ui": "library/jquery-ui/jquery-ui.min",
        "ace": "library/ace/src-min/ace"
    },
    map: {
        "*": {
            "css": "library/require/css.min"
        }
    },
    shim:{
        "jquery": {
            exports: "jquery"
        },
        "angular": {
            deps: ["jquery"],
            exports: "angular"
        },
        "bootstrap": {
            deps: ["jquery"],
            exports: "bootstrap"
        },
        "angular-bootstrap": {
            deps: ["angular"],
            exports: "angular-bootstrap"
        },
        "angular-route": {
            deps: ["angular"],
            exports:"angular-route"
        },
        "angular-touch": {
            deps: ["angular"],
            exports: "angular-touch"
        },
        "angular-sanitize": {
            deps: ["angular"],
            exports: "angular-sanitize"
        },
        "angular-animate": {
            deps: ["angular"],
            exports: "angular-animate"
        },
        "angular-cookies": {
            deps: ["angular"],
            exports: "angular-cookies"
        },
        "angular-locale": {
            deps: ["angular"],
            exports:"angular-locale"
        },
        "angular-file-upload": {
            deps: [
                "angular",
                "library/angular-file-upload/ng-file-upload-shim.min"
            ],
            exports: "angular-file-upload"
        },
        "ui-sortable": {
            deps: ["jquery" ,"jquery-ui", "angular"],
            exports:"ui-sortable"
        },
        "jquery-ui": {
            deps: ["jquery"],
            exports:"jquery-ui"
        }
    },
    urlArgs: "_v=" + window.BLACKDREAM_VERSION
});

require(
    ["angular", "framework/framework", "business/module"],
    function(angular){
        angular.element(document).ready(function() {
            angular.bootstrap(document,
                [
                    "framework",
                    "business"
                ]
            );
        });
    }
);