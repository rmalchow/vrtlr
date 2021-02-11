angular.module("angular-select",["ngRoute"]);
angular.module("angular-select")
.config(function($routeProvider,$locationProvider) {});

angular.module("angular-select").map = function(d) {
	if(angular.isUndefined(d)) {
		return {id : undefined};
	}
	
	return d;
}

angular.module("angular-select").directive(
	"anSelect",
	[ 
		"$compile","$templateCache","$timeout","$interval",
		function($compile,$templateCache,$timeout,$interval) {
			return {
				require : [ 'ngModel' ],
				transclude: false,
				scope : {
					ngDisabled : "=?",
					placeholder : "=?",
					allowReset : "=?",
					resolve : "&",
					query : "&",
					map : "&",
					ngChange : "&",
					ngModel : "="
				},
				link : function(scope, element, attrs, ngModelCtrl, transclude) {
					
					
					scope.placeholder = scope.placeholder || "Please select"; 
					
					console.log("scope: ",scope);
					
					var states = [ "inactive", "active", "empty", "query", "list" , "arrow" , "remove", "overlay" ];

					scope.active = false;
					scope.search = "";
					
					var templates = {};
					var elements = {};
					var ids = {};
					
					_.each(states,function(state) {
						var x = element.children("."+state);
						if(x.length > 0) {
							templates[state] = x[0].outerHTML;
						} else {
							console.log("default template for: "+state);
							var tName = state+".html";
							var tValue = $templateCache.get(tName);
							templates[state] = tValue;
						}
					});
					element.children().hide();
					
					_.each(states,function(state) {
						link = $compile(templates[state]);
						elements[state] = link(scope);
						ids[state] = angular.module("angular-select").UUID.generate();
						elements[state].attr("id", ids[state]);
						elements[state].attr("an-select-"+state,"true");
					});
					
					element.addClass("element");
					element.css({'position' : 'relative'});

					elements["inactive"].hide();
					element.append(elements["inactive"]);

					elements["empty"].hide();
					element.append(elements["empty"]);
					
					
					if(elements["inactive"].children(".icons").length > 0) {
						elements["icons"] = elements["inactive"].children(".icons").first(); 
						elements["icons"].append(elements["arrow"]);
						elements["arrow"].show();
						if(scope.allowReset) {
							elements["remove"].show();
							elements["remove"].addClass("remove");
							elements["icons"].append(elements["remove"]);
						}
					}
					
					elements["active"].show();

					element.append(elements["overlay"]);
					
					elements["overlay"].show();
					elements["overlay"].append(elements["active"]);
					elements["overlay"].append(elements["query"]);

					elements["list_parent"] = $("<div></div>");
					elements["overlay"].append(elements["list_parent"]);
					elements["overlay"].css({'background' : 'white'});
					
					elements["list_parent"].append(elements["list"]);
					elements["inactive"].css(
							{
								"position" : "absolute",
								"overflow" : "hidden", 
								"white-space" : "nowrap",
							}
						);
					
					resize = function() {
						
						var p = $(element).offset();
						
						elements["inactive"].css(
							{
								'position' : 'absolute',
								'width' : element.innerWidth(),
								'height' : element.innerHeight(),
								'top' : '0px',
								'left' : '0px',
								'z-index': 800
							}
						);
						
						elements["overlay"].css(
							{
								'position' : 'absolute',
								'width' : element.innerWidth(),
								'top' : '0px',
								'left' : '0px',
								'z-index': 900
							}
						);
						
					};
					
					$(window).resize(resize);
					resize();
					
					if(angular.isUndefined(scope.resolve)) {
						scope.resolve = function(a,callback) {
							console.log("WARNING: no resolve function defined!");
							callback(undefined);
						}
					} else {
						scope.resolve = scope.resolve();
					}
					
					if(angular.isUndefined(scope.active)) {
						scope.active = false;
					}

					if(angular.isUndefined(scope.allowReset)) {
						scope.allowReset = true;
					}
					
					if(angular.isUndefined(scope.query)) {
						scope.query = function(a,callback) {
							console.log("WARNING: no query function defined!");
							callback(a,[]);
						}
					} else {
						scope.query = scope.query();
					}

					if(angular.isUndefined(scope.ngChange)) {
						scope.ngChange = function() {};
					}
					
					scope.map = scope.map();
					if(angular.isUndefined(scope.map)) {
						scope.map = function(d) {
							if(angular.isUndefined(d)) {
								return {id : undefined};
							}
							if(!angular.isUndefined(d.fullPath) && d.fullPath.length > 0) {
								console.log("d: ",d);
								console.log("path: ",d.fullPath);
								var r = [];
								_.each(d.fullPath,function(pe) {
									console.log("adding: "+pe+" to: ",r);
									r.push(pe);
								});
								r.length = r.length - 1;
								d.text = r.join(" > ") + " | ";
							} 
							return d;
						}
					}
					
					scope.select = function(id) {
						scope.resolve(id, scope.set);
					}
					
					scope.set = function(obj) {
						obj = scope.map(obj);
						if(angular.isUndefined(obj.id)) {
							elements["empty"].show();						
							elements["inactive"].hide();						
						} else {
							elements["empty"].hide();						
							elements["inactive"].show();						
						}
						
						if(scope.ngModel!=obj.id) {
							console.log("setting object --- setting view value ", scope.ngModel);
							scope.active = false;
							scope.ngModel = obj.id;
						}
						scope.current = obj;
						
						console.log("calling ngChange: ",scope.ngChange);
						scope.ngChange();
						
						scope.active = false;
					};
	
					
					scope.queryCallback = function(term,results) {
						if(term!=scope.search) return;
						scope.results = results;
						if(results.length > 12) {
							results.length = 12;
							scope.hasMore = true;
						} else {
							scope.hasMore = false;
						}
						
						elements["query"].show();
						
						_.each(scope.results, function(r) {
							angular.module("angular-select").map(r);
						})
						resize();
					}
					
					scope.runQuery = function() {
						scope.query(scope.search, scope.queryCallback);
					}
					
					scope.$watch("ngModel",function(a,b,c) {
						if(a==b) {
							console.log("an - select: same value");
							return;
						}
						console.log("an - select: new value: "+b,scope.ngChange);
						scope.resolve(a,scope.set);
						scope.ngChange(b);
					});

					scope.$watch("active",function(a,b,c) {
						if(scope.active) {
							resize();
							$("#"+ids["overlay"]).show();
							$("#"+ids["query"]).children("input").focus();

							
							var sx = $(document).scrollTop();
							var px = $("#"+ids["overlay"]).offset().top;
							var vh = document.body.clientHeight;
							
							var toBottom = vh - (px - sx); 
							
							console.log("positions: "+sx+" - "+px+" - "+vh+" - "+toBottom);
							
						    if(toBottom < 300) {
						    		$('html, body').animate({scrollTop: sx+300}, 300);							
						    }
							scope.runQuery();
						} else {
							resize();
							$("#"+ids["overlay"]).hide();
						}
					});

					scope.$watch("ngDisabled",function(a,b,c) {
						console.log("disabled: "+scope.ngDisabled+": "+"#"+ids["arrow"]);
						if(!angular.isUndefined(elements["icons"])) {
							if(scope.ngDisabled) {
								elements["icons"].hide();
							} else {
								elements["icons"].show();
							}
						}
					});

					
					scope.$on('$destroy', function() {
				        _.each(
				        		elements,
				        		function(e) {
				        			e.remove();
				        		}
				        	);
					});

					scope.toggle = function() {
						if(scope.ngDisabled) return;
						scope.active = !scope.active;
					}
					
					
					$(document).on("click", function(e,a,b) {
						if(scope.ngDisabled) return;
						if(!scope.active) {
							if(
									$(e.target).attr("id")==ids["remove"] || 
									$(e.target).parents("#"+ids["remove"]).length > 0 
							) {
								scope.set(undefined);
							} else if(
									$(e.target).attr("id")==ids["inactive"] || 
									$(e.target).attr("id")==ids["empty"] || 
									$(e.target).parents("#"+ids["inactive"]).length > 0 ||
									$(e.target).parents("#"+ids["empty"]).length > 0
							) {
								scope.active = true;
							}
						} else if (scope.active) {
							scope.active = false;
						}
						scope.$apply();
					});
					
					scope.resolve(scope.ngModel,scope.set);
					
					scope.active = false;
					
					//$interval(resize,3000);
				}
			}
		}
	]
);
angular.module("angular-select").UUID = (function() {
  var self = {};
  var lut = []; for (var i=0; i<256; i++) { lut[i] = (i<16?'0':'')+(i).toString(16); }
  self.generate = function() {
    var d0 = Math.random()*0xffffffff|0;
    var d1 = Math.random()*0xffffffff|0;
    var d2 = Math.random()*0xffffffff|0;
    var d3 = Math.random()*0xffffffff|0;
    return lut[d0&0xff]+lut[d0>>8&0xff]+lut[d0>>16&0xff]+lut[d0>>24&0xff]+'-'+
      lut[d1&0xff]+lut[d1>>8&0xff]+'-'+lut[d1>>16&0x0f|0x40]+lut[d1>>24&0xff]+'-'+
      lut[d2&0x3f|0x80]+lut[d2>>8&0xff]+'-'+lut[d2>>16&0xff]+lut[d2>>24&0xff]+
      lut[d3&0xff]+lut[d3>>8&0xff]+lut[d3>>16&0xff]+lut[d3>>24&0xff];
  }
  return self;
})();