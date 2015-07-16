/*
 * JavaScript / Canvas teaching framwork 
 * (C)opyright Hartmut Schirmacher, hschirmacher.beuth-hochschule.de 
 *
 * Module: html_controller
 *
 * Defines callback functions for communicating with various 
 * HTML elements on the page, e.g. buttons and parameter fields.
 *
 */

 
/* requireJS module definition */
define(["jquery", "straight_line", "circle", "parametric_curve", "bezier_curve"], 
       (function($, StraightLine, Circle, ParamCurve, BezierCurve) {

    "use strict"; 
                
    /*
     * define callback functions to react to changes in the HTML page
     * and provide them with a closure defining context and scene
     */
    var HtmlController = function(context,scene,sceneController) {
    
    
        // generate random X coordinate within the canvas
        var randomX = function() { 
            return Math.floor(Math.random()*(context.canvas.width-10))+5; 
        };
            
        // generate random Y coordinate within the canvas
        var randomY = function() { 
            return Math.floor(Math.random()*(context.canvas.height-10))+5; 
        };
            
        // generate random color in hex notation
        var randomColor = function() {

            // convert a byte (0...255) to a 2-digit hex string
            var toHex2 = function(byte) {
                var s = byte.toString(16); // convert to hex string
                if(s.length == 1) s = "0"+s; // pad with leading 0
                return s;
            };
                
            var r = Math.floor(Math.random()*25.9)*10;
            var g = Math.floor(Math.random()*25.9)*10;
            var b = Math.floor(Math.random()*25.9)*10;
                
            // convert to hex notation
            return "#"+toHex2(r)+toHex2(g)+toHex2(b);
        };
        
        /*
         * event handler for "new line button".
         */
        $("#btnNewLine").click( (function() {
        
            // create the actual line and add it to the scene
            var style = { 
                width: Math.floor(Math.random()*3)+1,
                color: randomColor()
            };
                          
            var line = new StraightLine( [randomX(),randomY()], 
                                         [randomX(),randomY()], 
                                         style );
            scene.addObjects([line]);

            // deselect all objects, then select the newly created object
            sceneController.deselect();
            sceneController.select(line); // this will also redraw
                        
        }));
        
        
        /*
         * Event handler for the "new circle button".
         */
        $("#btnNewCircle").click((function() {
        	
        	var style = {
        			width:Math.floor(Math.random()*3)+1,
        			color:randomColor()
        	};
        	
        	var circle = new Circle(randomX(), randomY(), 50, style);
        	scene.addObjects([circle]);
        	sceneController.deselect();
        	sceneController.select(circle);
        }));
        
        $("#btnNewParamCurve").click((function() {
        	
        	var style = {
        			width:Math.floor(Math.random()*3)+1,
        			color:randomColor()
        	};
        	
        	// evaluate the given functions
        	var f = "350 + 100*Math.sin(t)";
        	var g = "150 + 100*Math.cos(t)";
        	
        	if (f && g) {
        		var paramCurve = new ParamCurve(f, g, 0, 2 * Math.PI, 20, true, style);
        		scene.addObjects([paramCurve]);
        		sceneController.deselect();
        		sceneController.select(paramCurve);
        	}
        }));
        
        $("#btnNewBezierCurve").click((function() {
        	var style = {width:Math.floor(Math.random()*3)+1, color:randomColor()};
        	
        	var f = "Math.pow(-(1-t), 3) * 100 + Math.pow(t,3) * 300";
        	var g = "3 * Math.pow((1-2), 2) * t * 50 - 3 * (1-t) * t * t * 50";
        	//var bezier = new ParamCurve(f, g, 0, 1, 20, true, style);
        	var bezier = new BezierCurve([20,100],[100, 50],[100, 150],[300,100], 20, true, style);
        	
        	scene.addObjects([bezier]);
        	sceneController.deselect();
        	sceneController.select(bezier);
        }));
        
        
        
        var showCtxMenu = (function(object) { 	
        	$(".ctx").hide();
        	$(".lineColor").attr("value", object.lineStyle.color);
    		$(".lineWidth").attr("value", object.lineStyle.width);
        	
        	// yeah! No way to check the type of the 'object'. <3 JS!
        	if (object.radius) {
        		$("#ctxCircle").show();
        		$(".circleRadius").attr("value", object.radius);
        	}
        	else if (object.segments) {
        		$(".curve_f").attr("value", object.f_text);
        		$(".curve_g").attr("value", object.g_text);
        		$(".t_min").attr("value", object.tMin);
        		$(".t_max").attr("value", object.tMax);
        		$(".segments").attr("value", object.segments);
        		if (object.showTickMarks) 
        			$(".showTicks").attr("checked", "checked");
        		
        		$("#ctxParamCurve").show();
        	}
        	else
        		$("#ctxLine").show();
        });
        
        
        // register callback for onObjChange and onSelection        
        sceneController.onObjChange(showCtxMenu);
        sceneController.onSelection(showCtxMenu);
        
        // bind a change event to all input.async fields
        $("input").bind('change', function() {
        	
        	var obj = sceneController.getSelectedObject();
        	
        	// dispatch for circles
        	if (obj.radius) {
        		obj.lineStyle.color = $("#ctxCircle .lineColor").attr("value");
        		obj.lineStyle.width = parseInt($("#ctxCircle .lineWidth").attr("value"));
        		obj.radius 	 		= parseInt($("#ctxCircle .circleRadius").attr("value"));
        	}
        	else if (obj.segments) {
        		obj.lineStyle.color = $("#ctxParamCurve .lineColor").attr("value");
        		obj.lineStyle.width = parseInt($("#ctxParamCurve .lineWidth").attr("value"));
        		obj.tMin			= parseFloat($("#ctxParamCurve .t_min").attr("value"));
        		obj.tMax			= parseFloat($("#ctxParamCurve .t_max").attr("value"));
        		obj.segments 		= parseInt($("#ctxParamCurve .segments").attr("value"));
        		
        		if ($("#ctxParamCurve .showTicks").attr("checked") == "checked") 
        			obj.showTickMarks = true;
        		else
        			obj.showTickMarks = false;
        		
        		// try to evaluate the functions
        		try {
        			eval("obj.f = function(t) { return (" + $("#ctxParamCurve .curve_f").attr("value") + ");}");
        			eval("obj.g = function(t) { return (" + $("#ctxParamCurve .curve_g").attr("value") + ");}");
        			obj.f_text = $("#ctxParamCurve .curve_f").attr("value");
        			obj.g_text = $("#ctxParamCurve .curve_g").attr("value");
        		} catch (ex) {
        			console.log("Error evaluating the functions!");
        		}
        	}
        	else {
        		obj.lineStyle.color = $("#ctxLine .lineColor").attr("value");
        		obj.lineStyle.width = parseInt($("#ctxLine .lineWidth").attr("value"));
        	}
        	
        	// draw the updates
        	scene.draw(context);
        });
        
    };

    // return the constructor function 
    return HtmlController;


})); // require 



            
