var login = angular.module('login',['ngStorage']);

login.controller('loginCtrl',function ($scope,$localStorage,$window){

	var username = $localStorage.username;
	var password = $localStorage.password;

	$scope.doLogin = function(){
		console.log("in doLogin method-----");
		if ($scope.username==null || $scope.password==null){
			$window.alert("username or password empty");
		} 
		if (username == $scope.username && password == $scope.password){
			$window.location.href = "../Assignment4/maps.html";
		} else{
			$window.alert("invalid username or password");
		}
	};
});