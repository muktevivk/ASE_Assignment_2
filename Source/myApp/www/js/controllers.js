angular.module('starter.controllers', [])

.controller('DashCtrl', function($scope,$http) {
         
    $scope.getFood=function(){
    console.log ("-----"+$scope.food);
    var resp = $http.get("https://api.foursquare.com/v2/venues/search?near=chicago&query=pizza&limit=5&client_id=P2IIU4YKUJ2ILBZMYLTDO42TUKACIOTJ5QVEYUK3F2ZAW0K3&client_secret=3A3ZTVHPSK4VU2T2YJLY0SSD43IH1Z32JAQNAEV4DWMLOSPZ&v=20160212");
        
            resp.success(function(data,status,headers,config){
            console.log("data : "+data.response.venues[1]);
            $scope.venues = data.response.venues;
          });
    	resp.error(function(data,status,headers,config){
    		alert("Failure during service call!!")
    	});  
   
  
  };
})

.controller('ChatsCtrl', function($scope, $http) {
  $scope.getWeather=function() {
    	
    	var resp = $http.get("http://api.openweathermap.org/data/2.5/weather?q="+$scope.city+"&units=imperial&appid=f37d72f01e44978f931f8518e5c588cc");
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

})

.controller('ChatDetailCtrl', function($scope, $stateParams, Chats) {
  $scope.chat = Chats.get($stateParams.chatId);
})


