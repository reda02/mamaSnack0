var app=angular.module("User",["ngRoute","ngCookies"]);

app.config(["$routeProvider",function($routeProvider){
    $routeProvider
    .when("/Login",{
        templateUrl: "view/login.html",
        controller: "UserController"
    }).when("/Verifier",{
        templateUrl: "view/EmailVerifie.html",
        controller: "UserController"
    }).when("/newPwd/:idPwd",{
        templateUrl: "view/newPassword.html",
        controller: "UserController"
    }).otherwise({
        redirectTo: "/Login"
    });
}]);