var signup = angular.module('signup',['ngStorage']);

signup.controller('signupController',function ($scope,$localStorage,$window) {
	
	
	$scope.doSignup = function(){
		$localStorage.username = $scope.username;
		$localStorage.password = $scope.password; 

		
		console.log("username: " + $scope.username);
		console.log("password: " + $scope.password);
		

		$window.location.href = "../Assignment4/maps.html"
	}
});