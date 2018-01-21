
app.directive('update', function(){
    return{
       restrict: 'A',
       link: function(scope, element, attrs){
          element.on('click', function(e){
              document.getElementById("edit-form").style.display="block";
          });
       }
    }
});
app.directive('remove', function(){
    return{
       restrict: 'A',
       link: function(scope, element, attrs){
          element.on('click', function(e){
              document.getElementById("delete-form").style.display="block";
          });
       }
    }
});
app.directive('roles', function(){
    return{
       restrict: 'A',
       link: function(scope, element, attrs){
          element.on('click', function(e){
              document.getElementById("roleTable").style.display="block";
          });
       }
    }
});
app.directive('plus', function(){
    return{
       restrict: 'A',
       link: function(scope, element, attrs){
          element.on('click', function(e){
              document.getElementById("roleUser").style.display="none";
          });
       }
    }
});
app.controller("AdminController",["$scope","$http",function($scope,$http){
    $scope.pageUsers=null;
    $scope.rolesByUser=null;
    $scope.addRoles=[];
    $scope.pageRoles=null;
    $scope.pageCuisines=null;
    $scope.pageCategories=null;
    $scope.pageProducts=null;
    $scope.pageCommandes=null;
    $scope.ligneCommande=null;
    $scope.idCommande=null;
    $scope.commandeData=null;
    var ligne=false;
    var n=0;
    $scope.UserPages=[];
    $scope.ProductPages=[];
    var Npage=0;
    $scope.page=1;
    
    
   
    //get users
    $http.get("http://localhost:8080/users")
    .then(function(res){
       $scope.pageUsers=res.data;
       if($scope.pageUsers.users.length > 0){
		   Npage=$scope.pageUsers.users.length / 5;
		   if(Npage % 1 != 0){
			   Npage = Npage+1;
		   }
	       for(i=1;i<=Npage;i++){
	          	$scope.UserPages.push(i);
	          }
       }

    },function(err) {
		console.log(err.status);
	});
    
    //get roles
    $http.get("http://localhost:8080/getRoles")
	.then(function(res) {
		$scope.pageRoles=res.data;
	});
    
    
    //get Cuisines
    $http.get("http://localhost:8080/getAllCuisines")
	.then(function(res){
		$scope.pageCuisines=res.data;
	});
    
    //get Categories
	$http.get("http://localhost:8080/getCats")
	.then(function(res){
		$scope.pageCategories=res.data;
	});
	
	
	
	//get Products
	$http.get("http://localhost:8080/getProduits")
	.then(function(res){
		$scope.pageProducts=res.data.produits;
		if($scope.pageProducts.length > 0){
			   Npage=$scope.pageProducts.length / 5;
			   if(Npage % 1 != 0){
				   Npage = Npage+1;
			   }
		       for(i=1;i<=Npage;i++){
		          	$scope.ProductPages.push(i);
		       }
		}
	});
	
	//get Commandes
	$http.get("http://localhost:8080/listCommandes")
	.then(function(res){
		$scope.pageCommandes=res.data.commandes;
	});
	
	//get Ligne de commade
	
	
    //get all users
    $scope.allUsers= function(){
    	$scope.pageUsers= null;
		$http.get("http://localhost:8080/users")
			.then(function(res){
				$scope.pageUsers=res.data;
			},function(err){
				
			});
	};
    //pagination
    $scope.changePage=function(k){
    	$scope.page=k;
    };
    $scope.previous=function(){
    	if($scope.page>1)
    		$scope.page-=1;
    };
    $scope.next=function(){
    	if($scope.page<parseInt(Npage))
    		$scope.page+=1;
    	console.log(Npage)
    };
    //get user
    $scope.clickedUser = null;
	$scope.selectUser = function(iduser){
		//$scope.clickedUser = user;
		console.log("id user : " +iduser);
		$http.get("http://localhost:8080/getuser/"+iduser)
		 .then(function(res){
			 $scope.clickedUser = res.data.user;
			 console.log("get user to update : " + JSON.stringify(data));
		 },function(err){
			 console.log(err);  
		 });
	};
	
	
	//update user
	$scope.saveUser=function(){
		var dataObj={
			idUser: $scope.clickedUser.idUser,
			nomUser: $scope.clickedUser.nomUser,
			prenomUser: $scope.clickedUser.prenomUser,
			email: $scope.clickedUser.email,
			adresse: $scope.clickedUser.adresse
		};
		var res=$http({
			method: 'POST',
			url: 'http://localhost:8080/updateuser',
			data: JSON.stringify(dataObj),
			headers: {'Content-Type': 'application/json; charset=utf-8'}
		})
		res.then(function(data) {
			$scope.allUsers();
		});
	};
	//delete user
	$scope.deleteUser=function(idUser){
		$scope.UserPages=[];
		$http.get("http://localhost:8080/deleteuser/"+ idUser)
			.then(function(res){
				$http.get("http://localhost:8080/users")
				.then(function(res){
					$scope.pageUsers=res.data;
					if($scope.pageUsers.users.length > 0){
						   Npage=$scope.pageUsers.users.length / 5;
						   if(Npage % 1 != 0){
							   Npage = Npage+1;
						   }else{
							   $scope.page-=1;
						   }
					       for(i=1;i<=Npage;i++){
					          	$scope.UserPages.push(i);
					          }
				       }
				},function(err){
					console.log(err.status);
				});
			});
	};
	//get Roles by user
	$scope.getUserRoles= function(idUser) {
		$scope.rolesByUser=null;
		$scope.addRoles=[];
		var j,cpt;
		$http.get("http://localhost:8080/getuser/"+idUser)
		 .then(function(res){
			 $scope.clickedRoleUser = res.data.user;
		 });
		$http.get("http://localhost:8080/rolebyUserId/"+idUser)
			.then(function(res) {
				$scope.rolesByUser=res.data;
				if($scope.rolesByUser.users.length>0){
					for(var i=0;i<$scope.pageRoles.users.length;i++){
						j=0;
						cpt=0;
						do{
							if($scope.rolesByUser.users[j].idRole != $scope.pageRoles.users[i].idRole){
								cpt++;
							}else{
								cpt=0;
							}
							if(cpt == $scope.rolesByUser.users.length){
								$scope.addRoles.push($scope.pageRoles.users[i]);
							}
							j++;
						}while((cpt != 0) && (j < $scope.rolesByUser.users.length));
					}
				}else{
					$scope.addRoles=$scope.pageRoles.users;
				}
			});
	};
	//get all Roles
	$scope.allRoles=function(){
		$scope.pageRoles=null;
		$http.get("http://localhost:8080/getRoles")
			.then(function(res) {
				$scope.pageRoles=res.data;
			},function(err){
				
			});
	};
	//add role to user
	$scope.roleToUser=function(idRole){
		$http.get("http://localhost:8080/attribueRole/"+$scope.clickedRoleUser.idUser+"/"+idRole)
			.then(function(res){
				$scope.getUserRoles($scope.clickedRoleUser.idUser);
			});
	};
	//remove role from a user
	$scope.removeRole=function(idRole){
		$http.get("http://localhost:8080/retirerRole/"+$scope.clickedRoleUser.idUser+"/"+idRole)
			.then(function(res){
				$scope.getUserRoles($scope.clickedRoleUser.idUser);
			});
	};
	
	
	//Add role
	$scope.addRole=function(){
		
		var dataObj={
				roleName:$scope.roleName
		};
		var res = $http({
			method: 'POST',
			url: 'http://localhost:8080/addrole',
			data: JSON.stringify(dataObj),
			headers: {'Content-Type': 'application/json;charset=utf-8'}
		})
		res.then(function(res){
			success();
			$scope.allRoles();
		});
	};
	function success(){
		document.getElementById('info').style.display = 'block';
		setTimeout(() => {
			document.getElementById('info').style.display = 'none';
		}, 4000);
		
		$scope.info="New Role added Successfully!";
	}
	//all cuisines
	$scope.allCui=function(){
		$scope.pageCuisines=null;
		$http.get("http://localhost:8080/getAllCuisines")
			.then(function(res){
				$scope.pageCuisines=res.data;
			});
	};
	//add Cuisine
	$scope.addCuisine=function(){
		var dataObj={
			nameCuisine : $scope.nameCuisine,
			langue : $scope.langue
		};
		var res=$http({
			method : 'POST',
			url : 'http://localhost:8080/addCuisines',
			data: JSON.stringify(dataObj),
			headers : {'Content-Type':'application/json;charset=utf-8'}
		})
		res.then(function(res){
			success();
			$scope.allCui();
		});
	};
	//all Categories
	$scope.allCat=function(){
		$scope.pageCategories=null;
		$http.get("http://localhost:8080/getCats")
		.then(function(res){
			$scope.pageCategories=res.data;
		});
	}
	//add Categorie
	$scope.addCategorie=function(){
		var dataObj={
			nomCategorie: $scope.nomCategorie,
			description: $scope.description
		};
		var res=$http({
			method: 'POST',
			url: 'http://localhost:8080/addCat',
			data: JSON.stringify(dataObj),
			headers: {'Content-Type':'application/json;charset=utf-8'}
		})
		res.then(function(){
			success();
			$scope.allCat();
		});
	};
	
	//get all Products
	$scope.allProducts=function(){
		$scope.pageProducts=null;
		$http.get("http://localhost:8080/getProduits")
		.then(function(res){
			$scope.pageProducts=res.data.produits;
		});
	};
	//add new products
	$scope.addProduct=function(){
		var CuiObj={
			idCuisine:$scope.selectedCui
		};
		var CatObj={
			idCategorie:$scope.selectedCat
		};
		var dataObj={
			description:$scope.newProduct.description,
			prix:$scope.newProduct.prix,
			dateAjoute: Date.now(),
			quantite:$scope.newProduct.quantite,
			designation:$scope.newProduct.designation,
			categorie: JSON.stringify(CatObj),
			cuisine: JSON.stringify(CuiObj)
		};
		var res=$http({
			method:"POST",
			url:"http://localhost:8080/addProduit",
			data:JSON.stringify(dataObj),
			headers:{'Content-Type':'application/json;charset=utf-8'}
		})
		res.then(function(res){
			$scope.allProducts();
		});
	};
	//get a product
	$scope.selectProduct=function(id){
		$scope.selectedProduct=null;
		$http.get("http://localhost:8080/getProduit/"+id)
		.then(function(res){
			$scope.selectedProduct=res.data.produit;
			alert($scope.selectedProduct.cuisine);
			//$http.get("http://localhost:8080/getCuisine/"+)
		});
	};
	
	//get all commandes
	$scope.allCommands=function(){
		$scope.pageCommmandes=null;
		$http.get("http://localhost:8080/listCommandes")
		.then(function(res){
			$scope.pageCommandes=res.data.commandes;
		});
	};

	$scope.showLigne=function(id){
		$scope.ligneCommande=null;
		if(!ligne){
			ligne=true;
            document.getElementById("arrow").src="../img/arrow-down.png";
            document.getElementById("arrow").style.opacity="1";
            $http.get("http://localhost:8080/getAllLigneDeCommande/"+id)
    		.then(function(res){
    			$scope.ligneCommande=res.data.LigneCommande;
    		});
		}else{
			ligne=false;
			document.getElementById("arrow").src="../img/arrow-right.png";
            document.getElementById("arrow").style.opacity="0.4";
		}
		
		
	};
	$scope.displayLigne=function(id){
		$scope.commandeData=null;
		$scope.cmd=null;
		document.getElementById("setting").style.display="none";
		document.getElementById("commandeInfo").style.display="block";
		$http.get("http://localhost:8080/getAllLigneDeCommande/"+id)
		.then(function(res){
			$scope.commandeData=res.data.LigneCommande;
		});
		$http.get("http://localhost:8080/getCommande/"+id)
		.then(function(res){
			$scope.cmd=res.data.commande;
		});
	};
	$scope.getLigne=function(Commande,Ligne){
		$scope.cmd=null;
		document.getElementById("setting").style.display="none";
		document.getElementById("commandeInfo").style.display="block";
		$scope.commandeData=[];
		$http.get("http://localhost:8080/getLigneDeCommandeById/"+Commande+"/"+Ligne)
		.then(function(res){
			$scope.commandeData.push(res.data.ligneCommande);
		});
		$http.get("http://localhost:8080/getCommande/"+Commande)
		.then(function(res){
			$scope.cmd=res.data.commande;
		});
	};
}]);