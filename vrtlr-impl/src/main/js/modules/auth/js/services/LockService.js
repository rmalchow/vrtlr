angular.module("shrtnr").factory(
	"LockService", 
	[ '$interval', '$timeout', '$location', 'Restangular', '$rootScope', function($interval,$timeout,$location,Restangular,$rootScope) {

		var s = {};
		
		s.list = function(success, error) {
			Restangular.all("api/locks").getList().then(success,error);
		}
		
		s.unlock = function(id, password,success, error) {
			Restangular.all("api/locks").one(id).customPOST("","",{password:password}).then(success,error);
		}
		
		
		return s;
		 
	}]
	
);
