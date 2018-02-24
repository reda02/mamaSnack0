var cpt=2;
var next=true;
var n=1;
app.directive('next', function(){
    return{
       restrict: 'A',
       link: function(scope, element, attrs){
          element.on('click', function(e){
        	  if(next)
        		  cpt++;
        	  else{
        		  cpt=cpt+3;
        		  next=true;
        	  } 
        	  document.getElementById("pag-left").style.visibility="visible";
        	  	var c = document.getElementById("listPlat").children;
        	  	if(cpt>= parseInt(attrs["next"])-1){
        	  		document.getElementById("pag-right").style.visibility="hidden";
        	  	}
        	  	c[cpt].scrollIntoView(false);
          });
       }
    }
});
app.directive('prev', function(){
    return{
       restrict: 'A',
       link: function(scope, element, attrs){
          element.on('click', function(e){
        	  if(!next)
        		  cpt--;
        	  else{
        		  cpt=cpt-3;
        		  next=false;
        	  }
        	  document.getElementById("pag-right").style.visibility="visible";
        	  	var c = document.getElementById("listPlat").children;
        	  	if(cpt <= 0){
        	  		document.getElementById("pag-left").style.visibility="hidden";
        	  	}
        	  	c[cpt].scrollIntoView(false);
          });
       }
    }
});
app.directive('select', function(){
    return{
       restrict: 'A',
       link: function(scope, element, attrs){
          element.on('click', function(e){
        	  $(function(){
        		 $("#edit-form").css("display","block"); 
        	  });
          });
       }
    }
});
app.controller("msgController",["$scope","$http","$routeParams","$cookies",function($scope,$http,$routeParams,$cookies){
	$(function(){
        $('.formMessage').submit(function(){
            socket.emit("chat message",$("#msg-sent").val());
            $("#msg-sent").val('');
            return false;
        });
        socket.on("new message",function(data){
        	
            if(data.des==$cookies.get("idUser")){
            	$(".messages").append("<div class='msg-block'><div class='sent'>"+data.msg+"</div></div>");
            }else{
            	if($cookies.get("idUser")==data.rec && $routeParams.mama==data.des){
            		$(".messages").append("<div class='msg-block'><div class='receive'>"+data.msg+"</div></div>");
            	}
            }
        });
        socket.emit("users",{des: $cookies.get("idUser"),rec:$routeParams.mama},function(){
        	
        });
        socket.on("load message",function(rows,des){
        	if($cookies.get("idUser")==des){
        	for(var i=0;i<rows.length;i++){        	
        		if(rows[i].id_user_dist==$cookies.get("idUser")){
        			$(".messages").append("<div class='msg-block'><div class='sent'>"+rows[i].contenu_mesg+"</div></div>");
        		}else{
        			$(".messages").append("<div class='msg-block'><div class='receive'>"+rows[i].contenu_mesg+"</div></div>");
        		}
        	}
        	}
        });
    });
}]);
app.controller("UserController",["$scope","$http","$routeParams","$cookies",function($scope,$http,$routeParams,$cookies){
	$scope.selectUser=null;
	$scope.pageUsers=null;
	$scope.nomComplet=null;
	$scope.produit=null;
	$http.get("http://localhost:8080/getuser/"+$cookies.get("idUser"))
	.then(function(res){
		$scope.selectUser=res.data.user;
		$scope.nomComplet=$scope.selectUser.prenomUser+" "+$scope.selectUser.nomUser;
	});
	$http.get("http://localhost:8080/getuser/"+$routeParams.mama)
	.then(function(res){
		$scope.selectMama=res.data.user;
		$scope.mamaComplet=$scope.selectMama.prenomUser+" "+$scope.selectMama.nomUser;
	});
	$scope.contacter=function(id){		
		location.href="/index.html#!/Message/"+id;
	};
	$scope.verifierEmail=function(){
		var dataObj={
				email:$scope.email
		}
		var res=$http({
			method:'POST',
			url:"http://localhost:8080/initEmail",
			data:JSON.stringify(dataObj),
			headers:{'Content-Type':'application/json; charset=utf-8'}
		})
		res.then(function(res){
			document.getElementById("emailError").style.display="block";			
			if(res.data.errMess=="OK"){
				document.getElementById("emailError").innerHTML="on a envoyé un message a votre email";
				document.getElementById("emailError").style.color="green";				
			}else{
				document.getElementById("emailError").style.color="red";
				document.getElementById("emailError").innerHTML="Cet Email n'existe pas au cet site web";
				}
		})
	};
	$scope.editPassword=function(){
		if($scope.pwd==$scope.confirm){
			var dataObj={
					idUser: $routeParams.idPwd,
					pwd:$scope.pwd
			}
			var res=$http({
				method:'POST',
				url:'http://localhost:8080/updatePwd',
				data:JSON.stringify(dataObj),
				headers:{'Content-Type':'application/json;charset=utf-8'}
			})
			res.then(function(res){
				if(res.data.errMess=="OK"){
					$http.get("http://localhost:8080/rolebyUserId/"+$routeParams.idPwd)
					.then(function(res){
						if(res.data.users[0].roleName == "user")
							location.href="/index.html";
						else{
							if(res.data.users[0].roleName == "admin")
								location.href="/app.html";
							else{
								location.href="/index.html#!/mamaProfil/"+$cookies.get("idUser");
							}
						}
					});
				}
			});
		}else{
			document.getElementById("emailError").style.color="red";
			document.getElementById("emailError").innerHTML="le mot de passe n'est pas identique avec la comfirmation";
		}
	};
	$scope.logIn=function(){
		var dataObj={
			email: document.getElementById("email").value,
			password: document.getElementById("password").value
		};
		var res=$http({
			method: 'POST',
			url:'http://localhost:8080/checkLogin',
			data: JSON.stringify(dataObj),
			headers: {'Content-Type': 'application/json; charset=utf-8'}
		})
		res.then(function(res){
			if(res.data.Msg=="OK"){
				$cookies.put("idUser",res.data.id);
				if(res.data.role[0].roleName == "user")
					location.href="/index.html";
				else{
					if(res.data.role[0].roleName == "admin")
						location.href="/app.html";
					else{
						location.href="/index.html#!/mamaProfil/"+$cookies.get("idUser");
					}
				}
			}
		});
	};
	
	$scope.editInfo=function(){
		var active=false;
		var e = document.getElementById("dispo");
		if(e.value=="oui")
			active=true;
		else
			active=false;
		var dataObj={
			mamaActived:active,
			ville:document.getElementById("city").value,
			adresse:document.getElementById("adresse").value
			//description
		};
		var res=$http({
			method:'POST',
			url:'http://localhost:8080/updateuser',
			data: JSON.stringify(dataObj),
			headers:{'Content-Type':'application/json; charset=utf-8'}
		})
		res.then(function(res){
			location.href="#!editProfil";
		});
	};
	$scope.toProfil=function(id){
		location.href="/index.html#!/mamaProfil/"+id;
	};
	$http.get("http://localhost:8080/getProduit/"+$routeParams.id)
	.then(function(res){
		$scope.produit=res.data.produit;
	});
	$http.get("http://localhost:8080/users")
    .then(function(res){
       $scope.pageUsers=res.data;
    },function(err) {
		console.log(err.status);
	})
	$http.get("http://localhost:8080/getCats")
	.then(function(res) {
		$scope.pageCategories=res.data;
	});
	$http.get("http://localhost:8080/getAllCuisines")
	.then(function(res) {
		$scope.pageCuisines=res.data;
	});
	
	$scope.removeProduct=function(){
		$(function() {
			    $("input[name='checkPlat']:checked").each(function() {
			    	$http.get("http://localhost:8080/deleteProduit/"+this.value)
			    	.then(function(res){
			    		$(function(){
			    			alert();
			    		});
			    	});
			    	$(this).parents('.get-plat').css("display","none");
			    });
			});
		 
	};
	$scope.selectProduct=function(id){
		$scope.select=true;
		$http.get("http://localhost:8080/getProduit/"+id)
		.then(function(res){
			$scope.selectedProduct=res.data.produit;
			if($scope.selectedProduct.steleted==true)
				$('#select').bootstrapToggle('on');
			else
				$('#select').bootstrapToggle('off');
		});
	};
		$http.get("http://localhost:8080/listCommandesParUser/"+$cookies.get("idUser"))
		.then(function(res){
			$scope.Commandes=res.data.commandes;
		});
	$scope.ligneCommande=function(id){
		$http.get("http://localhost:8080/getAllLigneDeCommande/"+id)
		.then(function(res){
			return res.data.LigneCommande;			
		});
	};
}]);

app.controller("mapController",["$scope","$http","$routeParams",function($scope,$http,$routeParams){
	$scope.usersCity=null;
	var map,geocoder;
	var infoWindow = new google.maps.InfoWindow();
	function clearAll(tb) {
        for (var i = 0; i < tb.length; i++) {
          tb[i].setMap(null);
        }
      }
	
	$scope.getProduit=function(id){
		location.href="#!Produit/"+id;
	};
	$scope.initMap=function() {
	    geocoder=new google.maps.Geocoder();
	    geocoder.geocode( { 'address': $routeParams.ville}, function(results, status) {
		    map = new google.maps.Map(document.getElementById('map'), {
		      zoom: 12,
		      center: new google.maps.LatLng(results[0].geometry.location.lat(),results[0].geometry.location.lng()),
		      mapTypeId: 'roadmap'
		    }); 
		    var searchArea=new google.maps.Circle();
		    var areaRadius=6500;
		    var markers=[];
		    var myZoom = {
		    		  start:  map.getZoom(),
		    		  end: map.getZoom()
		    	};
		    
		    google.maps.event.addListener(map, 'idle', function() {
		    	//alert();
		    	searchArea.setMap(null);
		    	//searchArea=null;
			    searchArea = new google.maps.Circle({
			        center: map.getCenter(),
			        radius: areaRadius,
			        map:map
			      });
			   
			    google.maps.event.addListener(map, 'zoom_changed', function() {
					myZoom.end = map.getZoom();
					var diff = myZoom.start - myZoom.end;
		    	    if (diff > 0) {
		    	        areaRadius =areaRadius* 2;
		    	    } else if (diff < 0) {
		    	    	areaRadius =areaRadius/ 2;
		    	    }
		    	    myZoom.start=map.getZoom();
		    	    
				});
			    for (var i = 0; i < markers.length; i++) {
			    	(function(i){
			    	if(!(google.maps.geometry.spherical.computeDistanceBetween(markers[i].position, searchArea.getCenter()) <= searchArea.getRadius())){
			    		markers[i].setMap(null);
			    		markers.splice(i,1);
			    		//alert();
			    	}
			    	})(i);
		        }
		    	$http.get("http://localhost:8080/users")
		        .then(function(res){
		        	$scope.usersMap=[];
		           $scope.usersCity=res.data.users;
		           $scope.usersCity.forEach(function(user) {
		    	   	    geocoder.geocode( { 'address': user.adresse}, function(results, status) {
			    	   	    	var nb=true;
			    	   	    	for (var i = 0; i < markers.length; i++) {
			    	   	    		if(markers[i].position.lat() == results[0].geometry.location.lat() && markers[i].position.lng() == results[0].geometry.location.lng()){
			    	   	    			if((google.maps.geometry.spherical.computeDistanceBetween(markers[i].position, searchArea.getCenter()) <= searchArea.getRadius())){
				    	   	    			nb=false;
			    	   	    			}else{
			    	   	    				markers[i].setMap(null);
			    				    		markers.splice(i,1);
			    	   	    			}
			    	   	    		}
			    	   	    	}
			    	   	    	//alert();
			    	   	    	if ((google.maps.geometry.spherical.computeDistanceBetween(results[0].geometry.location, searchArea.getCenter()) <= searchArea.getRadius()))  {
			    	   	    		//console.log(user.idUser);
			    	   	    		$http.get("http://localhost:8080/getuser/"+user.idUser)
				    		        .then(function(res){
				    		        	$scope.usersMap.push(res.data.user);
				    		        });
		    	   	    			var mk = new google.maps.Marker({
		    	   	                    position: results[0].geometry.location,
		    	   	                    map: map
		    	   	                });
		    	   	    			
		    	   	                var img="";
		    	   	             
		    	   	                $http.get("http://localhost:8080/produitsByUser/"+user.idUser)
		    	   	                .then(function(res){
		    	   	                	
		    	   	                	res.data.produits.forEach(function(p){
		    	   	                		img=img+"<div class='formMap'><div class='imgMap'><img src='img/plat.jpg'></div><p>"+p.description+"</p></div>";
		    	   	                	});
		    	   	                	mk.content="<div class='modalMap'><span class='push push-left'>&lang;</span><span class='push push-right'>&rang;</span><h4>"+user.prenomUser+" "+user.nomUser+"</h4><div class='userMap'>"+img+"</div><h5>Tel: "+user.tel+"</h5><h5>Adresse: "+user.adresse+"</h5></div>";
		    	   	                	google.maps.event.addListener(infoWindow, 'domready', function() {
		    	   	                		
		    		   	             		$('.push-right').click(function(){
		    		   	             			/*if(n>=res.data.produits.length){
		    		   	             				n=0;
		    		   	             			}
		    		   	             			n++;
		    		   	             			var p=n-1;
		    		   	             			$(".userMap .formMap:NTH-CHILD("+n+")").css("display","block");
		    		   	             			$(".userMap .formMap:NTH-CHILD("+p+")").css("display","none");*/
		    		   	             		});
		    	   	                	});
		    	   	                });
		    	   	                
		       	                mk.addListener('click', function() {
		    	   	            	 infoWindow.setContent(mk.content);
		    	   	            	 infoWindow.open(map, mk);
		    	   	            });
		       	             markers.push(mk);
		       	                nb=true;
				    		} 
		    	   	        //}else{alert(status);}
		    	   	    });
		    	   	    
		       	});
		        },function(err) {
		    		console.log(err.status);
		    	});
			    
			});
	    });
	};
	
	$scope.initMap();
	/*$http.get("http://localhost:8080/userbyCity/"+$routeParams.ville)
    .then(function(res){
       $scope.usersCity=res.data.users;
       $scope.usersCity.forEach(function(user) {
	   	    geocoder.geocode( { 'address': user.adresse}, function(results, status) {
	   	        //if(status=="OK"){
	   	                //getImage(book.cover);
	   	                var marker = new google.maps.Marker({
	   	                    position: results[0].geometry.location,
	   	                    //icon: image,
	   	                    map: map,
	   	                    //zoom: 
	   	                });
	   	                var img="";
	   	             
	   	                $http.get("http://localhost:8080/produitsByUser/"+user.idUser)
	   	                .then(function(res){
	   	                	
	   	                	res.data.produits.forEach(function(p){
	   	                		img=img+"<div class='formMap'><div class='imgMap'><img src='img/plat.jpg'></div><p>"+p.description+"</p></div>";
	   	                	});
	   	                	marker.content="<div class='modalMap'><span class='push push-left'>&lang;</span><span class='push push-right'>&rang;</span><h4>"+user.prenomUser+" "+user.nomUser+"</h4><div class='userMap'>"+img+"</div><h5>Tel: "+user.tel+"</h5><h5>Adresse: "+user.adresse+"</h5></div>";
	   	                	google.maps.event.addListener(infoWindow, 'domready', function() {
	   	                		
		   	             		$('.push-right').click(function(){
		   	             			/*if(n>=res.data.produits.length){
		   	             				n=0;
		   	             			}
		   	             			n++;
		   	             			var p=n-1;
		   	             			$(".userMap .formMap:NTH-CHILD("+n+")").css("display","block");
		   	             			$(".userMap .formMap:NTH-CHILD("+p+")").css("display","none");
		   	             		});
	   	                	});
	   	                });
	   	                
   	                marker.addListener('click', function() {
	   	            	 infoWindow.setContent(marker.content);
	   	            	 infoWindow.open(map, marker);
	   	            });
	   	        //}else{alert(status);}
	   	    });
   	});
    },function(err) {
		console.log(err.status);
	});*/
	
	google.maps.event.addListener(infoWindow, 'domready', function() {
		var iwOuter = $('.gm-style-iw');
		var iwBackground = iwOuter.prev();
		iwBackground.children(':nth-child(2)').css({'display' : 'none'});
		iwBackground.children(':nth-child(4)').css({'display' : 'none'});
		var iwCloseBtn = iwOuter.next();
		iwCloseBtn.css({'display':'none'});
	});
	/*google.maps.event.addListener(map, 'bounds_changed', function() {
		
	});*/
	
}]);