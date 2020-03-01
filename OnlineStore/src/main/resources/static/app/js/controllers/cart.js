angular.module('onlineStoreApp')
    // Creating the Cart Controller
    .controller('cartController', function($http, $scope, AuthService) {

        $scope.user = AuthService.user;

        $scope.productsInfo = [];
        $scope.cartPrice = cart.price;

        checkCart();
        getProductsInfo();

        $scope.reduceProductCount = function() {
            var productId = $(this)[0].productInfo.product.id;
            var price = $(this)[0].productInfo.price;

            if(cart.products[productId] !== undefined){
                cart.products[productId] --;
                cart.price -= price;

                if(cart.products[productId] === 0){
                    delete cart.products[productId];
                }
            }
            localStorage.setItem('cart'+$scope.user.username, JSON.stringify(cart));
            refresh();
        };

        $scope.increaseProductCount = function () {
            var productId = $(this)[0].productInfo.product.id;
            var price = $(this)[0].productInfo.price;

            if(cart.products[productId] !== undefined){
                cart.products[productId] ++;
                cart.price += price;
            }

            localStorage.setItem('cart'+$scope.user.username, JSON.stringify(cart));
            refresh();
        };

        function getProductsInfo() {
            $scope.productsInfo = [];
            for (var prod in cart.products){
                loadProductInfo(prod);
            }
        }

        function refresh(){
            getProductsInfo();
            $scope.cartPrice = cart.price;
        }

        function loadProductInfo(id) {
            $http({
                method: 'GET',
                url: '/products/productInfo/' + id
            }).then(function (response) {
                    var productInfo;
                    productInfo = response.data;
                    productInfo.orderCount = cart.products[id];
                    $scope.productsInfo.push(productInfo);
                }
            );
        }

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
    });