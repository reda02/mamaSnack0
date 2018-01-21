

$(document).ready(function(){
	
});
var app=angular.module("myApp",["ngRoute"]);
app.config(["$routeProvider",function($routeProvider){
    $routeProvider
    .when("/Users",{
        templateUrl: "view/Users.html",
        controller: "AdminController"
    }).when("/RoleCat",{
    	templateUrl: "view/Role.html",
        controller: "AdminController"
    }).when("/Produit",{
    	templateUrl:"view/Produit.html",
    	controller:"AdminController"
    }).when("/Commande",{
    	templateUrl:"view/Commande.html",
    	controller:"AdminController"
    }).otherwise({
        redirectTo: "/Commande"
    });
}]);

app.controller("Ctrl",["$scope","$http",function($scope,$http){
	
	$scope.langue=null;
	$scope.lang="fr";
	
	$http.get("../language.json")
	.then(function(res){
		$scope.langue=res.data["fr"];
	});
	$scope.changed=function(lang){
		$http.get("../language.json")
		.then(function(res){
			$scope.langue=res.data[lang];
		});
	};
}]);