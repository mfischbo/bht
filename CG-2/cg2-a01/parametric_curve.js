define(["util", "vec2", "scene", "point_dragger"],
		(function(Util, vec2, Scene, PointDragger) {
	
	"use strict";
	
	/**
	 * Constructor for a parametric curve
	 * @param f, g - References to the functions of the curve
	 * @param tMin, tMax - Defines the range the curve is drawn within
	 * @param segs	- Defines the amount of segments the curve is splitted into
	 * @param showTickMarks - Whether tick marks should be shown or not
	 * @param style 	- Defines the drawing style of the curve 
	 */
	var ParamCurve = function(f, g, tMin, tMax, segs, showTickMarks, style) {
		
		try {
			this.f_text = f;
			this.g_text = g;
			
			eval("this.f = function(t) { return (" + f + "); }");
			eval("this.g = function(t) { return (" + g + "); }");
		} catch (ex) {
			console.log("Error in param curve constructor.");
		}
			
		this.tMin = tMin;
		this.tMax = tMax;
		this.segments = segs;
		this.showTickMarks = showTickMarks;
		
		this.lineStyle = style || {width:2, color:"#0000ff"};
	};
			
	
	/**
	 * Draws each segment of the curve 
	 */
	ParamCurve.prototype.draw = function(context) {
		
		var tSize = ((this.tMax - this.tMin) / this.segments);
		
		context.beginPath();
		for (var t = this.tMin; t < this.tMax; t+= tSize) {
			
			var fv = this.f(t);
			var gv = this.g(t);
			
			context.moveTo(fv, gv);
			context.lineTo(this.f(t + tSize), this.g(t + tSize));
			
			if (this.showTickMarks) {
				var vc1 = [this.f(t-tSize), this.g(t-tSize)];
				var vc2 = [this.f(t+tSize), this.g(t+tSize)];
				var norm = [vc2[1] - vc1[1], -1 * (vc2[0] - vc1[0])];
				norm = vec2.mult(norm, 5 / vec2.length(norm));
				context.moveTo(-1 * norm[0] + fv, -1 * norm[1] + gv);
				context.lineTo(norm[0] + fv, norm[1] + gv);
			}
		}
		
		context.lineWidth = this.lineStyle.width;
		context.strokeStyle = this.lineStyle.color;
		context.stroke();
	}
	
	/**
	 * Returns whether the curve has been hit or not 
	 */
	ParamCurve.prototype.isHit = function(context, mPos) {
		var tSize = ((this.tMax - this.tMin) / this.segments);
		for (var t = this.tMin; t < this.tMax; t+= tSize) {
			
			var point1 = [this.f(t), this.g(t)];
			var point2 = [this.f(t + tSize), this.g(t + tSize)];
			
			var k = vec2.projectPointOnLine(mPos, point1, point2);
			var p = vec2.add(point1, vec2.mult(vec2.sub(point2, point1), k));
			var d = vec2.length(vec2.sub(p, mPos));
			
			if (d <= (this.lineStyle.width/2) + 2)
				return true;
		}
		return false;
	}
	
	
	
	/**
	 * Always returns an empty array, since the curve does not display draggers.
	 */
	ParamCurve.prototype.createDraggers = function() {
		return [];
	}
				
		
	return ParamCurve
}));