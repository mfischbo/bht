define(["util", "scene", "point_dragger"], 
       (function(Util,Scene, PointDragger) {

    "use strict";
    
    var PolygonDragger = function(positions) {
    	
    	this.positions = positions;
    };
    
    PolygonDragger.prototype.draw = function(context) {
    	
    	for (var i=0; i < (this.positions.length -1); ++i) {
    		context.moveTo(this.positions[i][0], this.positions[i][1]);
    		context.lineTo(this.positions[i+1][0], this.positions[i+1][1]);
    	}
    	
    	context.lineWidth = 1;
		context.strokeStyle = "#0000ff";
		context.stroke();
    }
    

    PolygonDragger.prototype.mouseDrag = function(dragEvent) {
    	
    }
    
    return PolygonDragger;

})); // define