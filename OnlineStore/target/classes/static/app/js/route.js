angular.module('onlineStoreApp').config(function($stateProvider, $urlRouterProvider) {

    // the ui router will redirect if a invalid state has come.
    $urlRouterProvider.otherwise('/page-not-found');
    // parent view - navigation state
    $stateProvider.state('nav', {
        abstract : true,
        url : '',
        views : {
            'nav@' : {
                templateUrl : 'app/views/nav.html',
                controller : 'NavController'
            }
        }
    }).state('login', {
        parent : 'nav',
        url : '/login',
        views : {
            'content@' : {
                templateUrl : 'app/views/login.html',
                controller : 'LoginController'
            }
        }
    }).state('users', {
        parent : 'nav',
        url : '/users',
        data : {
            role : 'ADMIN'
        },
        views : {
            'content@' : {
                templateUrl : 'app/views/users.html',
                controller : 'UsersController'
            }
        }
    }).state('home', {
        parent : 'nav',
        url : '/',
        views : {
            'content@' : {
                templateUrl : 'app/views/home.html',
                controller : 'HomeController'
            }
        }
    }).state('page-not-found', {
        parent : 'nav',
        url : '/page-not-found',
        views : {
            'content@' : {
                templateUrl : 'app/views/page-not-found.html',
                controller : 'PageNotFoundController'
            }
        }
    }).state('access-denied', {
        parent : 'nav',
        url : '/access-denied',
        views : {
            'content@' : {
                templateUrl : 'app/views/access-denied.html',
                controller : 'AccessDeniedController'
            }
        }
    }).state('register', {
        parent : 'nav',
        url : '/register',
        views : {
            'content@' : {
                templateUrl : 'app/views/register.html',
                controller : 'RegisterController'
            }
        }
    }).state('categories', {
        parent : 'nav',
        url : '/categories',
        views : {
            'content@' : {
                templateUrl : 'app/views/categories/categories_list.html',
                controller : 'categoryController',
                controllerAs: 'ctrl'
            }
        }
    }).state('showCategory', {
        parent : 'nav',
        url : '/categories/category/:categoryID',
        views : {
            'content@' : {
                templateUrl : 'app/views/categories/show_category.html',
                controller : 'categoryController',
                controllerAs: 'ctrl'
            }
        }
    }).state('createCategory', {
        parent : 'nav',
        url : '/categories/addCategory',
        views : {
            'content@' : {
                templateUrl : 'app/views/categories/create_category.html',
                controller : 'categoryController',
                controllerAs: 'ctrl'
            }
        }
    }).state('updateCategory', {
        parent : 'nav',
        url : '/categories/update/:categoryID',
        views : {
            'content@' : {
                templateUrl : 'app/views/categories/update_category.html',
                controller : 'categoryController',
                controllerAs: 'ctrl'
            }
        }
    }).state('attributes', {
        parent : 'nav',
        url : '/attribute',
        views : {
            'content@' : {
                templateUrl : 'app/views/attributes/attributes_list.html',
                controller : 'attributeController'
            }
        }
    }).state('showProduct', {
        parent : 'nav',
        url : '/products/product/:productID',
        views : {
            'content@' : {
                templateUrl: 'app/views/products/show_product.html',
                controller: 'productController'
            }
        }
    }).state('createProduct', {
        parent : 'nav',
        url : '/products/addProduct/:categoryID',
        views : {
            'content@' : {
                templateUrl: 'app/views/products/create_product.html',
                controller: 'productController'
            }
        }
    }).state('updateProduct', {
        parent : 'nav',
        url : '/products/update/:productID',
        views : {
            'content@' : {
                templateUrl: 'app/views/products/update_product.html',
                controller: 'productController'
            }
        }
    }).state('cart', {
        parent : 'nav',
        url : '/cart',
        views : {
            'content@' : {
                templateUrl: 'app/views/cart.html',
                controller: 'cartController'
            }
        }
    });
});