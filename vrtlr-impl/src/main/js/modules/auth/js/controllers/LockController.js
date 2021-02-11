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
