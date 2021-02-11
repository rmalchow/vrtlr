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
