
var app=angular.module("mamaSnackApp", ["ui.bootstrap"]);



app.directive("w3TestDirective", function() {  
    return {
        template : "I was made in a directive constructor!"
    };
});

 app.controller("myController", function($scope,$http) {

	 $scope.newUser = {};
	 $scope.page = 1;
	 $scope.pageUsers= null ; 
	 
	 //get users 
	 $http.get("http://localhost:8080/users")
	 .success(function(data){
		 $scope.pageUsers= data;
	 })
	 .error(function(err){
		 console.log(err);  
	 });
	 
	 
	 // get users by Role (afficher les users en fonction liste déroulante)
	 $scope.search2 =function(selectedRole){
		 console.log(selectedRole);
	 $scope.pageUsers= null ; 
	 $http.get("http://localhost:8080/userbyRole/"+selectedRole)
	 .success(function(data){
		 $scope.pageUsers= data;		 
	 })
	 .error(function(err){
		 console.log(err); 
	 });
	 };
	

	 // get Roles (liste déroulante)
	 $scope.roles= null ; 
	 $http.get("http://localhost:8080/getRoles")
	 .success(function(data){
		 $scope.roles= data;	
		
	 })
	 .error(function(err){
		 console.log(err); 
	 });
	 
	 //la pagination 
		$scope.pageChanged = function() {
		  var startPos = ($scope.page - 1) * 5;
		  //$scope.displayItems = $scope.totalItems.slice(startPos, startPos + 5);
		  console.log($scope.page);
		};
		
		// get ALL users 
		  $scope.AfficherAll = function() {
				 $scope.pageUsers= null ; 
				 $http.get("http://localhost:8080/users")
				 .success(function(data){
					 $scope.pageUsers= data;
				 })
				 .error(function(err){
					 console.log(err); 
				 });				  
		  };
		  
		  
		// add new user
		  $scope.saveUser = function(){
			  var dataObj = {
					    nomUser : $scope.newUser.username,
					    email : $scope.newUser.email,
					    prenomUser : $scope.newUser.prenomUser
				};	
			    console.log( "kokot message: " + JSON.stringify(dataObj));
				var res = $http({
				    method :'POST',
					url     : 'http://localhost:8080/adduser',
					data    : JSON.stringify(dataObj),
					headers:{'Content-Type': 'application/json; charset=utf-8'}
				})
				res.success(function(data) {
					$scope.AfficherAll();
					//$scope.pageUsers.push({nomUser : $scope.newUser.username,email : $scope.newUser.email});
					$scope.message = data;
					console.log("Saving...is"+$scope.message);
					$scope.info = "New User Added Successfully!";
				});
				res.error(function(data) {
					$scope.info = "New User not Added !";
					alert( "failure message: " + JSON.stringify({data: data}));
				});
			};
			
			
			// get user to update 
			$scope.clickedUser = null;
			$scope.selectUser = function(iduser){
				//$scope.clickedUser = user;
				console.log("id user : " +iduser);
				$http.get("http://localhost:8080/getuser/"+iduser)
				 .success(function(data){
					 $scope.clickedUser = data.user;
					 console.log("get user to update : " + JSON.stringify(data));
				 })
				 .error(function(err){
					 console.log(err);  
				 });
			};
			
			
			// update user
			  $scope.updateUser = function(){
				  var dataObjup = {
						    idUser : $scope.clickedUser.idUser,
						    nomUser : $scope.clickedUser.nomUser,
						    email : $scope.clickedUser.email,
						    prenomUser : $scope.clickedUser.prenomUser
					};	
				    console.log( "updateUser json: " + JSON.stringify(dataObjup));
					var res = $http({
					    method :'POST',
						url     : 'http://localhost:8080/updateuser',
						data    : JSON.stringify(dataObjup),
						headers:{'Content-Type': 'application/json; charset=utf-8'}
					})
					res.success(function(data) {
						$scope.AfficherAll();
						//$scope.pageUsers.push({nomUser : $scope.newUser.username,email : $scope.newUser.email});
						$scope.message = data;
						console.log("Saving...is"+$scope.message);
						$scope.info = "New User updated Successfully!";
					});
					res.error(function(data) {
						$scope.info = "New User not updated !";
						alert( "failure message: " + JSON.stringify({data: data}));
					});
				};
				
				
				// delete user 
			
				$scope.deleteUser = function(iduser){
					//$scope.clickedUser = user;
					console.log("id clickeddeleteUser  : " +iduser);
					$http.get("http://localhost:8080/deleteuser/"+iduser)
					 .success(function(data){
						 $scope.AfficherAll();
						 console.log("delete user : " + JSON.stringify(data));
					 })
					 .error(function(err){
						 console.log(err);  
					 });
				};
 });

 


