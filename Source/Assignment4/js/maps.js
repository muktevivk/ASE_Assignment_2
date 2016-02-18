var app = angular.module('maps',[]);

app.controller('mapController',function($scope,$http){
	var map;
    var mapOptions;

    $scope.initialize = function () {
        navigator.geolocation.getCurrentPosition(function (position) {

            var pos = new google.maps.LatLng(
            position.coords.latitude,
            position.coords.longitude);

            var mapOptions = {
                zoom: 16,
                center: pos
            };

            map = new google.maps.Map(document.getElementById('map'),
            mapOptions);

            var marker = new google.maps.Marker({
                position: pos,
                map: map
            });
        });
    };

    var directionsDisplay = new google.maps.DirectionsRenderer({
        draggable: true
    });
    var directionsService = new google.maps.DirectionsService();

    $scope.initialize = function () {
          var pos = new google.maps.LatLng(39.0997,-94.5783); 
          var mapOptions = {
                zoom: 3,
                center: pos
            };

            map = new google.maps.Map(document.getElementById('map'),
            mapOptions);
     };

     $scope.getDirection= function(){
     	var end = $scope.destination;
            var start = $scope.source;

            var request = {
                origin: start,
                destination: end,
                travelMode: google.maps.TravelMode.DRIVING
            };

            directionsService.route(request, function (response, status) {
                if (status == google.maps.DirectionsStatus.OK) {
                    directionsDisplay.setMap(map);
                    directionsDisplay.setDirections(response);
                    //console.log(status);
                }
           
        });
    };
    google.maps.event.addDomListener(window, 'load', $scope.initialize);

    $scope.getClimateSource=function() {
    	
    	var resp = $http.get("http://api.openweathermap.org/data/2.5/weather?q="+$scope.source+"&units=imperial&appid=f37d72f01e44978f931f8518e5c588cc");
    	resp.success(function(data,status,headers,config){
    		console.log("data : "+data.weather[0].id);
    		$scope.temperatureSource = data.main.temp;
    		$scope.pressureSource = data.main.pressure;
    		$scope.descSource = data.weather[0].description;
    		
    	});
    	resp.error(function(data,status,headers,config){
    		alert("Failure during service call!!")
    	});
    };

    $scope.getClimateDestination = function () {
    	var res = $http.get("http://api.openweathermap.org/data/2.5/weather?q="+$scope.destination+"&units=imperial&appid=44db6a862fba0b067b1930da0d769e98");
    	res.success(function(data,status,headers,config){
    		console.log("destination data: " +data.weather[0].id );
    		$scope.temperatureDestination = data.main.temp;
    		$scope.pressureDestination = data.main.pressure;
    		$scope.descDestination = data.weather[0].description;
    	});
    	res.error(function(data,status,headers,config){
    		alert("Failure during service call 1!!")
    	});
    };
});