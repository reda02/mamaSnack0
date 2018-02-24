
var app=angular.module("User",["ngRoute","angular.filter","ngCookies"]);
app.config(["$routeProvider",function($routeProvider){
    $routeProvider
    .when("/Acceuil",{
        templateUrl: "view/Acceuil.html",
        controller: "UserController"
    }).when("/Compte",{
    	templateUrl: "view/Compte.html",
        controller: "UserController"
    }).when("/Users/:ville",{
    	templateUrl: "view/UsersByCity.html",
        controller: "UserController"
    }).when("/Produit/:id",{
    	templateUrl: "view/oneProduct.html",
        controller: "UserController"
    }).when("/mamaProfil/:mama",{
    	templateUrl: "view/mamaProfil.html",
        controller: "UserController"
    }).when("/editProfil",{
    	templateUrl: "view/editProfil.html",
        controller: "UserController"
    }).when("/Message/:mama",{
    	templateUrl: "view/message.html",
        controller: "UserController"
    }).when("/Panier",{
    	templateUrl: "view/Panier.html",
        controller: "UserController"
    }).otherwise({
        redirectTo: "/Acceuil"
    });
}]);