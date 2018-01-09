'use strict';
var App = angular.module('uploadApp',[]);

App.factory('docService', ['$http', '$q', 'urls', function ($http, $q, urls) {

            var factory = {
                saveDoc: saveDoc,
                findDoc: findDoc
            };

            return factory;

            function saveDoc(file) {
                var deferred = $q.defer();
                var dataObj={
                	
                		   "designation": "luul",
                		    "description": "plat tajine poulet",
                		    "prix": 15,
                		    "steleted": false,
                		    "photo1": "photo1",
                		    "quantite": 27,
                		    "categorie": {"idCategorie":1},
                		    "cuisine":{"idCuisine":2}
                		 
            		};
                var formData = new FormData();
                formData.append('file', file);
                formData.append('ad',JSON.stringify(dataObj));
                $http.post('http://localhost:8080/addProduit', formData,{
                    transformRequest : angular.identity,
                    headers : {
                        'Content-Type' : undefined
                    }})
                    .then(
                        function (response) {
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            alert(errResponse.data.errorMessage);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            };

            function findDoc(docId) {
                var deferred = $q.defer();
                $http.get(urls.DOC_URL + '/'+docId)
                    .then(
                        function (response) {
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            alert(errResponse.data.errorMessage);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }
        }
    ]);