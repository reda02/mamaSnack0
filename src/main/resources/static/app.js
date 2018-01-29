app.controller('docController',
    ['$scope', '$rootScope','docService','$http','$cookies', function($scope, $rootScope, docService, $http,$cookies) {

        $scope.file = '';

       $scope.upload = function(){      
            var file = $scope.file;
            var select=false
     		if(document.getElementById("selected").checked)
     			select=true;
     		else
     			select=false;
            
            var dataObj={
         		   "designation": document.getElementById("designation").value,
         		    "description": document.getElementById("description").value,
         		    "prix": document.getElementById("prix").value,
         		    "steleted": select,
         		    "photo1": "photo1",
         		    "quantite": document.getElementById("quantite").value,
         		    "categorie": {"idCategorie":$scope.selectedCat},
         		    "cuisine":{"idCuisine":$scope.selectedCui},
         		    "user":{"idUser":$cookies.get("idUser")}
     		};
            alert(file);
            docService.saveDoc(file,dataObj)
                .then(
                    function (response) {
                        alert("file uploaded successfully.");
                        $http.get("http://localhost:8080/doc").success(
                            function(response) {
                            	alert("success");
                                $rootScope.docList = response.data;
                            });
                    },
                    function (errResponse) {
                    		alert("err");
                    }
                );
        };
        
        $scope.edit = function(id){
            var file = $scope.file;
            var select=false
     		if(document.getElementById("select").checked)
     			select=true;
     		else
     			select=false;
            
            var dataObj={
            		"idProduit":id,
         		   	"designation": $scope.selectedProduct.designation,
         		    "description": $scope.selectedProduct.description,
         		    "prix": $scope.selectedProduct.prix,
         		    "steleted": select,
         		    "photo1": "photo1",
         		    "quantite": $scope.selectedProduct.quantite,
         		   // "categorie": {"idCategorie":$scope.selectedCat},
         		    "cuisine":{"idCuisine":$scope.selectedCui},
         		    "user":{"idUser":$cookies.get("idUser")}
     		};
            alert(file);
            docService.editDoc(file,dataObj)
                .then(
                    function (response) {
                        alert("file uploaded successfully.");
                        $http.get("http://localhost:8080/doc").success(
                            function(response) {
                            	alert("success");
                                $rootScope.docList = response.data;
                            });
                    },
                    function (errResponse) {
                    		alert("err");
                    }
                );
        };
    }
    ]);

app.constant('urls', {
    DOC_URL: 'http://localhost:8080/doc/'
});

app.directive('fileModel', [ '$parse', function($parse) {
    return {
        restrict : 'A',
        link : function(scope, element, attrs) {
            var model = $parse(attrs.fileModel);
            var modelSetter = model.assign;

            element.bind('change', function() {
                scope.$apply(function() {
                    modelSetter(scope, element[0].files[0]);
                });
            });
        }
    };
} ]);

app.run(function($rootScope, $http) {
    $http.get("http://localhost:8080/doc")
	 .then(function(res){
		 $rootScope.docList = res.data;		 
	 });	 
    
});
