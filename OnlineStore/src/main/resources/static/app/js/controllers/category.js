angular.module('onlineStoreApp')
    // Creating the Angular Controller
    .controller('categoryController', function($http, $scope, AuthService, $stateParams, $location) {

        $scope.user = AuthService.user;

        $scope.category = {};
        $scope.allCategories = [];
        $scope.productsCategories = [];
        $scope.products = [];
        $scope.productsInfo = [];
        $scope.attributes = [];
        $scope.categoryAttributes = [];
        $scope.parentSelections = [];
        $scope.parentCategory = null;
        $scope.categoryForm = {
            id: -1,
            parentCategoryId: -1,
            name: "",
            categAttrs: [],
            checked: []
        };

        refreshingData();
        checkCart();

        if($stateParams.categoryID !== undefined ){
            getCategory($stateParams.categoryID);
            getCategoryAttributes($stateParams.categoryID);
            getProducts($stateParams.categoryID);
            getProductsInfo($stateParams.categoryID);

            $http({
                method: 'GET',
                url: 'http://localhost:8080/categories/' + $stateParams.categoryID
            }).then(function (response) {
                $scope.category = response.data;
            }, function (error) {
            });
        }

        $scope.updateCategory = function() {

            if($scope.parentCategory != null){
                $scope.categoryForm.parentCategoryId = $scope.parentCategory.id;
            }

            $http({
                method: "PUT",
                url: '/categories',
                data: angular.toJson($scope.categoryForm),
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(function (response) {
                    $scope.category = response.data;
                    $location.path("/categories/category/" + $scope.category.id)
                }
            );
        };

        $scope.createCategory = function() {
            if($scope.parentCategory.id != null){
                $scope.categoryForm.parentCategoryId = $scope.parentCategory.id;
            }
            $http({
                method: "POST",
                url: '/categories',
                data: angular.toJson($scope.categoryForm),
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(function (response) {
                    $scope.category = response.data;
                    $location.path("/categories/category/" + $scope.category.id)
                }
            );
        };

        $scope.deleteCategory = function(categ) {
            console.log("category.id: " + categ.id);
            console.log("category.name: " + categ.name);
            $http({
                method: 'DELETE',
                url: '/categories/' + categ.id
            }).then(
                refreshingData(),
                $location.path("categories")
            );
        };

        $scope.addToCart = function () {
            if(cart.userId === -1){
                cart.userId = $scope.user;
            }

            var productId = $(this)[0].productInfo.product.id;
            var price = $(this)[0].productInfo.price;

            if(cart.products[productId] != undefined){
                cart.products[productId] ++;
            }else{
                cart.products[productId] = 1;
            }

            cart.price = cart.price + price;
            localStorage.setItem('cart'+$scope.user.username, JSON.stringify(cart));
        };

        function checkCart() {
            if(localStorage.getItem('cart'+ $scope.user.username)){
                cart = JSON.parse(localStorage.getItem('cart'+ $scope.user.username))
            }else{
                cart = {
                    userId: $scope.user.id,
                    products: {},
                    price: 0
                }
            }
        }

        function getCategory(categoryId) {
            $http({
                method: 'GET',
                url: 'http://localhost:8080/categories/' + categoryId
            }).then(
                function(res) { // success
                    $scope.category = res.data;
                    $scope.categoryForm.id = res.data.id;
                    $scope.categoryForm.name = res.data.name;
                    $scope.parentCategory = res.data.parentCategory;
                },
                function(res) { // error
                    console.log("Error: " + res.status + " : " + res.data);
                }
            );
        }

        function getCategoryAttributes(categoryId) {
            $http({
                method: 'GET',
                url: 'http://localhost:8080/categories/' + categoryId + '/getAttributes'
            }).then(
                function(res) { // success
                    $scope.categoryAttributes = res.data;
                    fillCategoryForm($scope.categoryAttributes);
                },
                function(res) { // error
                    console.log("Error: " + res.status + " : " + res.data);
                }
            );
        }

        function getProductsInfo(categoryId) {
            $http({
                method: 'GET',
                url: 'http://localhost:8080/categories/getProducts/' + categoryId
            }).then(
                function(res) { // success
                    $scope.productsInfo = res.data;
                },
                function(res) { // error
                    console.log("Error: " + res.status + " : " + res.data);
                }
            );
        }

        function getProducts(categoryId) {
            $http({
                method: 'GET',
                url: 'http://localhost:8080/categories/' + categoryId + '/product_list'
            }).then(
                function(res) { // success
                    $scope.products = res.data;
                },
                function(res) { // error
                    console.log("Error: " + res.status + " : " + res.data);
                }
            );
        }

        function fillCategoryForm(attributes) {
            for(var i = 0; i< $scope.attributes.length; i++){
                $scope.categoryForm.categAttrs[i] = $scope.attributes[i].name;
            }

            for(var j = 0; j < attributes.length; j++){
                $scope.categoryForm.checked[j] = false;
            }
        }

        function refreshingData() {
            $http({
                method: 'GET',
                url: 'http://localhost:8080/categories'
            }).then(function (response) {
                $scope.allCategories = response.data;
            }, function (error) {
            });

            $http({
                method: 'GET',
                url: 'http://localhost:8080/categories/productCategories'
            }).then(function (response) {
                $scope.productsCategories = response.data;
            }, function (error) {
            });

            $http({
                method: 'GET',
                url: 'http://localhost:8080/attributes'
            }).then(function (response) {
                $scope.attributes = response.data;
                fillCategoryForm($scope.attributes);
            }, function (error) {
            });
        }
    });