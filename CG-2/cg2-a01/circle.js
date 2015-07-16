define(["util", "vec2", "scene", "point_dragger"], 
       (function(Util,vec2,Scene,PointDragger) {
			
	"use strict";
	
	
	/**
	 * Constructor for a new Circle
	 * @param origin  The origin of the circle
	 * @param radius  The radius of the circle
	 */
	var Circle = function(x, y, radius, style) {
		
		this.origin = [x,y];//origin;
		this.radius = radius;
		this.lineStyle = style || {width: "2", color: "#0000AA"};
	};
	
	
	/**
	 * Draws the actual circle
	 */
	Circle.prototype.draw = function(context) {
		
		context.beginPath();
		context.arc(this.origin[0], this.origin[1], Math.abs(this.radius), 0, 360 * Math.PI);
		context.lineWidth = this.lineStyle.width;
		context.strokeStyle = this.lineStyle.color;
		context.stroke();
	};
	
	
	/**
	 * Checks whether the mouse is pointed on the circles line with a tolerance of 2 px
	 */
	Circle.prototype.isHit = function(context, mPos) {
	
		var dx = mPos[0] - this.origin[0];
		var dy = mPos[1] - this.origin[1];

		var sum = dx*dx + dy*dy;
		var radMin = (this.radius-2) * (this.radius-2);
		var radMax = (this.radius+2) * (this.radius+2);
				
		return (sum <= radMax) && (sum >= radMin )
	};
	
	
	/**
	 * Returns a list of draggers to manipulate this circle
	 */
	Circle.prototype.createDraggers = function() {
		
        var draggerStyle = { radius:4, color:this.lineStyle.color, width:0, fill:true }
        var draggers = [];
        
        var _circle = this;
        
        // callback for the position of the position dragger
        var getOrigin = function() {return _circle.origin;};
        
        // callback for the position of the radius dragger
        var getRadius = function() {return [_circle.origin[0] + _circle.radius, _circle.origin[1]]};
        
        // callback to set a new position to the circle
        var setOrigin = function(dragEvent) {_circle.origin = dragEvent.position;};
        
        // callback to set a new radius for the circle
        var setRadius = function(dragEvent) {
        	_circle.radius = Math.abs(dragEvent.position[0] - _circle.origin[0]);
        }
        
        draggers.push(new PointDragger(getOrigin, setOrigin, draggerStyle));
        draggers.push(new PointDragger(getRadius, setRadius, draggerStyle));
        return draggers;
	};	
	
	return Circle;
}));