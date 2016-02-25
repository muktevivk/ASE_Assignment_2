var app = angular.module('maps',[]);

app.controller('mapController',function($scope,$http){

navigator.geolocation.getCurrentPosition(function (position) {
            console.log(position.coords.latitude);
            console.log(position.coords.longitude);
            var resp = $http.get("https://api.foursquare.com/v2/venues/search?ll="+position.coords.latitude+","+position.coords.longitude+"&query=theaters&limit=5&client_id=P2IIU4YKUJ2ILBZMYLTDO42TUKACIOTJ5QVEYUK3F2ZAW0K3&client_secret=3A3ZTVHPSK4VU2T2YJLY0SSD43IH1Z32JAQNAEV4DWMLOSPZ&v=20160212");
            resp.success(function(data,status,headers,config){
            console.log("data : "+data.response.venues[1]);
            $scope.venues = data.response.venues;
            
        });
        resp.error(function(data,status,headers,config){
            alert("Failure during service call!!")
        });
            
        });
    
   
    

    
});