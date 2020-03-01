angular.module('onlineStoreApp')
    // Creating the Angular Controller
    .controller('attributeController', function($http, $scope, AuthService) {
        $scope.user = AuthService.user;

        $scope.message = "";
        $scope.btnText = 'create';
        $scope.attributes = [];
        $scope.attributeTypes = [{name: "text", id: 1}, {name: "number", id: 2}];
        $scope.attributeForm = {
            id: -1,
            name: "",
            type: $scope.attributeTypes[0]
        };

        $scope.submitAttribute = function() {

            var method = "";
            var url = "";
            var action = "";

            if ($scope.attributeForm.id === -1) {
                method = "POST";
                url = '/attributes';
                action = "create"

            } else {
                method = "PUT";
                url = '/attributes';
                action = "update";
            }

            $http({
                method: method,
                url: url,
                data: angular.toJson($scope.attributeForm),
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(function (response) {
                if(response.data === "") {
                    $scope.message = "An attribute named: '" + $scope.attributeForm.name + "' already exist!";
                }else {
                    $scope.message = "Attribute " + action + "d successful";
                }

                _refreshData();
                _clearFormData();
            });
        };

        $scope.createAttribute = function() {
            $scope.btnText = "create";
            _clearFormData();
        };

        _refreshData();

        $scope.deleteAttribute = function(attribute) {
            $http({
                method: 'DELETE',
                url: '/attributes/' + attribute.id
            }).then(_success, _error);
        };

        // In case of edit
        $scope.editAttribute = function(attribute) {
            $scope.attributeForm.id = attribute.id;
            $scope.attributeForm.name = attribute.name;
            $scope.attributeForm.type = $scope.attributeTypes[attribute.type - 1];
            $scope.message = "";

            $scope.btnText = 'update';
        };

        function _refreshData() {
            $http({
                method: 'GET',
                url: '/attributes'
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
            _refreshData();
            _clearFormData();
            $scope.message = "";
        }

        function _error(res) {
            var data = res.data;
            var status = res.status;
            var header = res.headers;
            var config = res.config;
            alert("Error: " + status + ":" + data);
        }

        // Clear the form
        function _clearFormData() {
            $scope.attributeForm.id = -1;
            $scope.attributeForm.name = "";
            $scope.attributeForm.type = $scope.attributeTypes[0];
        }
    });