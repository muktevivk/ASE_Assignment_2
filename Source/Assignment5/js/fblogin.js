var fbLoginApp = angular.module('fblogin',[]);
window.fbAsyncInit = function() {
    FB.init({
      appId      : '1648294948764600',
      xfbml      : true,
      version    : 'v2.5'
    });
  };

  (function(d, s, id){
     var js, fjs = d.getElementsByTagName(s)[0];
     if (d.getElementById(id)) {return;}
     js = d.createElement(s); js.id = id;
     js.src = "//connect.facebook.net/en_US/sdk.js";
     fjs.parentNode.insertBefore(js, fjs);
   }(document, 'script', 'facebook-jssdk'));

fbLoginApp.controller('fblogincontroller',function($scope,$window){

  $scope.doSocialLogin=function () {
    FB.login(function(response) {
          if (response.authResponse) {
           console.log('Welcome!  Fetching your information.... ');
           FB.api('/me', function(response) {
             console.log('Good to see you, ' + response.name + '.');
           });
          } else {
           console.log('User cancelled login or did not fully authorize.');
          }
      });
   /*FB.getLoginStatus(function(response){
        if (response.status === 'connected'){
      console.log(response);
    }else if (response.status==='not_authorized'){
      console.log("you are not authorized");
    }else{
            
    }
    });*/
   //$window.location.href="../home.html"
  }



});

