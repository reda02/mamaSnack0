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
						//mama role page
					}
				}
			}
		});
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
	});
	
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