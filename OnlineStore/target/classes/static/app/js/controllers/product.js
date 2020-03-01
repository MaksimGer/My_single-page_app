angular.module('onlineStoreApp')
    // Creating the Angular Controller
    .controller('productController', function($http, $scope, AuthService, $stateParams, $location) {

        $scope.user = AuthService.user;
        $scope.attributeTypes = [{name: "text", id: 1}, {name: "number", id: 2}];

        $scope.productParams = [];
        $scope.product = {};
        $scope.category = {};
        $scope.attributes = [];
        $scope.comments = [];
        $scope.commentForm = {
            productId: -1,
            userId: $scope.user.id,
            text: ''
        };
        $scope.productForm = {
            id: -1,
            name: "",
            categoryId: -1,
            prodAttrs: [],
            values: []
        };

        if($stateParams.productID !== undefined){
            getProduct($stateParams.productID);
            getProductParams($stateParams.productID);
            getAttributes($stateParams.productID);

        }

        if($stateParams.categoryID !== undefined){
            $http({
                method: 'GET',
                url: 'http://localhost:8080/categories/' + $stateParams.categoryID
            }).then(function (response) {
                $scope.category = response.data;
                $scope.productForm.categoryId = response.data.id;
            }, function (error) {
            });

            $http({
                method: 'GET',
                url: 'http://localhost:8080/attributes/getAllByCategory/' + $stateParams.categoryID
            }).then(function (response) {
                $scope.attributes = response.data;
                setAttributesToProductForm();
            }, function (error) {
            });
        }

        $scope.setType = function(index){
            var type = "text";
            if($scope.attributes[index].type === 2){
                type = "number";
            }
            return type;
        };

        $scope.updateProduct = function() {
            $http({
                method: "PUT",
                url: '/products',
                data: angular.toJson($scope.productForm),
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(function (response) {
                    $scope.product = response.data;
                    $location.path("/products/product/" + $scope.product.id)
                }
            );
        };

        $scope.createProduct = function() {
            $http({
                method: "POST",
                url: '/products',
                data: angular.toJson($scope.productForm),
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(function (response) {
                    $scope.product = response.data;
                    $location.path("/products/product/" + $scope.product.id)
                }
            );
        };

        $scope.addComment = function(){
            $http({
                method: "POST",
                url: '/comments',
                data: angular.toJson($scope.commentForm),
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(function (response) {
                    getComments();
                }
            );
        };

        $scope.deleteProduct = function(product) {
            $http({
                method: 'DELETE',
                url: '/products/' + product.id
            }).then($location.path("categories/category/" + $scope.product.category.id), _success, _error);
        };

        function setAttributesToProductForm() {
            for(var i = 0; i< $scope.attributes.length; i++){
                $scope.productForm.prodAttrs[i] = $scope.attributes[i].name;
            }
        }

        function getProductParams(productId) {
            $http({
                method: 'GET',
                url: '/products/getProductParams/' + productId
            }).then(
                function(res) { // success
                    $scope.productParams = res.data;
                    fillProductForm(productId);
                },
                function(res) { // error
                    console.log("Error: " + res.status + " : " + res.data);
                }
            );
        }

        function getProduct(productId) {
            $http({
                method: 'GET',
                url: '/products/' + productId
            }).then(
                function(res) { // success
                    $scope.product = res.data;
                    $scope.productForm.id = res.data.id;
                    $scope.productForm.name = res.data.name;
                    $scope.productForm.categoryID = res.data.category.id;
                    $scope.commentForm.productId = res.data.id;
                    getComments();
                },
                function(res) { // error
                    console.log("Error: " + res.status + " : " + res.data);
                }
            );
        }

        function fillProductForm(productId){
            $scope.productForm.id = productId;
            $scope.productForm.name = $scope.product.name;
            $scope.productForm.categoryID = $scope.product.category.id;

            for(var i = 0; i< $scope.productParams.length; i++ ){
                $scope.productForm.values[i] = $scope.productParams[i].value;
                $scope.productForm.prodAttrs[i] = $scope.productParams[i].attribute.name;
            }
        }

        function getComments() {
            $http({
                method: 'GET',
                url: '/comments/' + $scope.productForm.id
            }).then(
                function(res) { // success
                    $scope.comments = res.data;
                },
                function(res) { // error
                    console.log("Error: " + res.status + " : " + res.data);
                }
            );
        }

        function getAttributes(productID) {
            $http({
                method: 'GET',
                url: '/products/getAttr/' + productID
            }).then(
                function(res) { // success
                    $scope.attributes = res.data;
                },
                function(res) { // error
                    console.log("Error: " + res.status + " : " + res.data);
                }
            );
        }

        function _success(res) {
            _clearFormData();
        }

        function _error(res) {
            var data = res.data;
            var status = res.status;
            var header = res.headers;
            var config = res.config;
            alert("Error: " + status + ":" + data);
        }

        // Clear the forms
        function _clearFormData() {
            $scope.productForm.id = -1;
            $scope.productForm.name = "";
            $scope.productForm.categoryID = -1;
        }
    });