define(["util", "vec2", "scene", "point_dragger", "parametric_curve", "polygon_dragger"],
		(function(Util, vec2, Scene, PointDragger, ParamCurve, PolygonDragger) {
	
	"use strict";
	
	/**
	 * Constructor for the Bezier Curve
	 * @param p0 The vector where to start the curve
	 * @param p1 The first control vector
	 * @param p2 The second control vector
	 * @param p3 The vector where to end the curve
	 * @param segments The amount of segments to draw
	 * @param showTicks Whether to draw tick marks or not
	 * @param style The style of the curve to be drawn
	 */
	
	var BezierCurve = function(p0, p1, p2, p3, segments, showTicks, style) {
		this.p0 = p0;
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
		this.segments = segments;
		this.showTicks = showTicks;
		
		this.lineStyle = style || {lineWidth:2, color:"#ff0000"};
		
		ParamCurve.call(this,
			"Math.pow((1-t),3) * this.p0[0] + 3 * Math.pow((1-t),2)*t * this.p1[0] + 3 * (1-t) *t*t * this.p2[0] + Math.pow(t,3) * this.p3[0]",
			"Math.pow((1-t),3) * this.p0[1] + 3 * Math.pow((1-t),2)*t * this.p1[1] + 3 * (1-t) *t*t * this.p2[1] + Math.pow(t,3) * this.p3[1]",
				0, 1, 20, true);
	}

	
	/**
	 * Draws the Bezier Curve
	 * @param context - The Canvas context where to draw to
	 */
	
	BezierCurve.prototype.draw = ParamCurve.prototype.draw;
	
	/**
	 * Returns whether the curve has been hit 
	 * @param context The canvas context the curve is drawn on
	 * @param mPos The vector of the mouse position where the click occured
	 */
	BezierCurve.prototype.isHit = ParamCurve.prototype.isHit;
	
	/**
	 * Returns an array of PointDraggers enabling the user to manipulate the curve
	 */
	BezierCurve.prototype.createDraggers = function() {
		var style = {radius:4, color:"#ff0000", width:0, fill:true};
		var draggers = [];
		var _curve = this;
		
		var getControlPoint1 = function() {return _curve.p0;};
		var getControlPoint2 = function() {return _curve.p1;};
		var getControlPoint3 = function() {return _curve.p2;};
		var getControlPoint4 = function() {return _curve.p3;};
		
		var setControlPoint1 = function(dragEvent) {_curve.p0 = dragEvent.position;};
		var setControlPoint2 = function(dragEvent) {_curve.p1 = dragEvent.position;};
		var setControlPoint3 = function(dragEvent) {_curve.p2 = dragEvent.position;};
		var setControlPoint4 = function(dragEvent) {_curve.p3 = dragEvent.position;};
		
		var polygonDragger = new PolygonDragger([_curve.p0, _curve.p1, _curve.p2, _curve.p3]);
		
		draggers.push(new PointDragger(getControlPoint1, setControlPoint1, style, "P1"));
		draggers.push(new PointDragger(getControlPoint2, setControlPoint2, style, "P2"));
		draggers.push(new PointDragger(getControlPoint3, setControlPoint3, style, "P3"));
		draggers.push(new PointDragger(getControlPoint4, setControlPoint4, style, "P4"));
		draggers.push(polygonDragger);
		return draggers;
	}
	return BezierCurve;
}));