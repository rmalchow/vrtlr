
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
