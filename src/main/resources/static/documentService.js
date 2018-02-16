'use strict';
//var App = angular.module('uploadApp',[]);

app.factory('docService', ['$http', '$q', 'urls', function ($http, $q, urls) {
            var factory = {
                saveDoc: saveDoc,
                findDoc: findDoc,
                editDoc: editDoc
            };

            return factory;

            function saveDoc(file,file2,file3,file4,file5,dataObj) {
                var deferred = $q.defer();                
                var formData = new FormData();
                formData.append('file', file);
                formData.append('file', file2);
                formData.append('file', file3);
                formData.append('file', file4);
                formData.append('file', file5);
                formData.append('json',JSON.stringify(dataObj));
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
            }
            function editDoc(file,dataObj) {
            	alert("fact");
                var deferred = $q.defer();                
                var formData = new FormData();
                formData.append('file', file);
                formData.append('json',JSON.stringify(dataObj));
                $http.post('http://localhost:8080/updateProduit', formData,{
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
            }

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