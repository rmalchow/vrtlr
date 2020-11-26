if (!String.prototype.includes) {
	String.prototype.includes = function(search, start) {
		if (typeof start !== 'number') {
			start = 0;
		}
		if (start + search.length > this.length) {
			return false;
		} else {
			return this.indexOf(search, start) !== -1;
		}
	};
}
if (!Array.prototype.includes) {
	Array.prototype.includes = function(search, start) {
		if (typeof start !== 'number') {
			start = 0;
		}
		if (start + search.length > this.length) {
			return false;
		} else {
			return this.indexOf(search, start) !== -1;
		}
	}; 
}  


var app = angular.module("shrtnr",["templates","ngRoute","angular-plugin","angular-select","restangular","angularMoment"]); 

angular.module("shrtnr").config(
	[ '$httpProvider','RestangularProvider', function($httpProvider,RestangularProvider) {
		console.log("URL: "+window.location.pathname);
		RestangularProvider.setBaseUrl(window.location.pathname);
	}]
);


angular.module("shrtnr").run(
		['PluginMenuService', '$location', '$rootScope',
		function(PluginMenuService, $location, $rootScope) {

			var locks = {
					"order": 999, 
					"title" : "Locks",
					"id" : "LOCKS",
					"visible" :true, 
					"active" :false,
					"controller" : "LockController",
					"controllerAs" : "locks",
					"templateUrl" : "/auth/templates/locks.html",
					"reloadOnSearch" : false
				};
			
			PluginMenuService.addItem("","/locks",locks);
			PluginMenuService.setDefault("/locks");
			
		}]

);

angular.module("shrtnr").controller(
	"LockController", 
	[ '$timeout', '$rootScope', '$location', '$routeParams', 'LockService',  function($timeout,$rootScope,$location,$routeParams, LockService) {

		var locks = this;

		
		locks.reset = function() {
			locks.pin = "XXXXXX";
			_.each(locks.locks, function(lock){
				lock.status = 0;
			})
		}
			
		locks.reset();
		
		locks.type = function(number) {
			_.each(locks.locks, function(lock){
				lock.status = 1;
			})
			locks.pin = locks.pin || "";
			locks.pin = locks.pin + number;
			while(locks.pin.length > 6) locks.pin = locks.pin.substring(1);
		}
		
		
		locks.update = function() {
			LockService.list(
				function(ls) {
					locks.locks = ls;
					console.log("locks: "+ls.length);
				}
			);
		}
		
		locks.tryUnlock = function(lock) {
			LockService.unlock(
					lock.shrtnrId,
					locks.pin,
					function(x) {
						// success
						lock.status = 2;
						$timeout(function() { lock.status=1 },3000);
					},
					function(x) {
						// failed
						$timeout(locks.reset,3000);
						lock.status = -2;
					}
				);
		}
		
		
		locks.numbers = [];
		
		for(i=0; i<10;i++) {
			locks.numbers.push(i);
		}
		
		
		locks.update();
		
	}]
	
);

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
