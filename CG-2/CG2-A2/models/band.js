/*
 * WebGL core teaching framwork 
 * (C)opyright Hartmut Schirmacher, hschirmacher.beuth-hochschule.de 
 *
 * Module: Band
 *
 * The Band is made of two circles using the specified radius.
 * One circle is at y = height/2 and the other is at y = -height/2.
 *
 */


/* requireJS module definition */
define(["util", "vbo"], 
       (function(Util, vbo) {
       
    "use strict";
    
    /*
     */
    var Band = function(gl, config) {
    
        // read the configuration parameters
        config = config || {};
        var radius   = config.radius   || 1.0;
        var height   = config.height   || 0.1;
        var segments = config.segments || 20;
        this.asWireframe = config.asWireframe;
        
        window.console.log("Creating a " + (this.asWireframe? "Wireframe " : "") + 
                            "Band with radius="+radius+", height="+height+", segments="+segments ); 
    
        // generate vertex coordinates and store in an array
        var coords = [];
        var indices= [];
        var off = 2;
        for(var i=0; i<=segments; i++) {
        
            // X and Z coordinates are on a circle around the origin
            var t = (i/segments)*Math.PI*2;
            var x = Math.sin(t) * radius;
            var z = Math.cos(t) * radius;
            // Y coordinates are simply -height/2 and +height/2 
            var y0 = height/2;
            var y1 = -height/2;
            
            // add two points for each position on the circle
            // IMPORTANT: push each float value separately!
            coords.push(x,y0,z);
            coords.push(x,y1,z);
            
            // add indices for each 2nd iteration
            if (!this.asWireframe) {
	            if (i%2 == 0 && i > 0) {
	            	indices.push(off-2, off-1, off, 
	            				 off,   off-1, off+1);
	            	
	            	indices.push(off,   off+1, off+2, 
	            			     off+2, off+1, off+3);
	            	off += 4;
	            }
            } else {
            	if (i%2 == 0 && i > 0) {
            		indices.push(
            				off, 	off+1, 
            				off+1, 	off+3, 
            				off+3, 	off+2,
            				off+2,  off,
            				off,    off-2,
            				off+3,  off-1);
            		off += 4;
            	}
            }
        };  
        
        // create vertex buffer object (VBO) for the coordinates
        this.coordsBuffer = new vbo.Attribute(gl, { "numComponents": 3,
                                                    "dataType": gl.FLOAT,
                                                    "data": coords 
                                                  } );
        
        this.indexBuffer = new vbo.Indices(gl,{"indices":indices});
    };

    // draw method clears color buffer and optionall depth buffer
    Band.prototype.draw = function(gl,program) {
    
        // bind the attribute buffers
        this.coordsBuffer.bind(gl, program, "vertexPosition");

        this.indexBuffer.bind(gl);
        if (!this.asWireframe) {
        	gl.enable(gl.POLYGON_OFFSET_FILL);
        	gl.polygonOffset(2.0, 2);
        	gl.drawElements(gl.TRIANGLES, this.indexBuffer.numIndices(), gl.UNSIGNED_SHORT, 0);
        
        } else {
        	gl.drawElements(gl.LINES, this.indexBuffer.numIndices(), gl.UNSIGNED_SHORT, 0);
        }
    };
        
    // this module only returns the Band constructor function    
    return Band;

})); // define

    
