/*
  *
 * Module main: CG2 Aufgabe 2 Teil 2, Winter 2012/2013
 * (C)opyright Hartmut Schirmacher, hschirmacher.beuth-hochschule.de 
 *
 */


/* 
 *  RequireJS alias/path configuration (http://requirejs.org/)
 */

requirejs.config({
    paths: {
    
        // jquery library
        "jquery": [
            // try content delivery network location first
            'http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min',
            //If the load via CDN fails, load locally
            './lib/jquery-1.7.2.min'],
            
        // gl-matrix library
        "gl-matrix": "./lib/gl-matrix-1.3.7"

    }
});


/*
 * The function defined below is the "main" module,
 * it will be called once all prerequisites listed in the
 * define() statement are loaded.
 *
 */

/* requireJS module definition */
define(["jquery", "gl-matrix", "util", "webgl-debug", 
        "program", "shaders", "animation", "html_controller", "scene_node", 
        "models/triangle", "models/cube", "models/band", "models/Arm", "models/Head"], 
       (function($, glmatrix, util, WebGLDebugUtils, 
                    Program, shaders, Animation, HtmlController, SceneNode,
                    Triangle, Cube, Band, Arm, Head ) {

    "use strict";
    
    /*
     *  This function asks the HTML Canvas element to create
     *  a context object for WebGL rendering.
     *
     *  It also creates awrapper around it for debugging 
     *  purposes, using webgl-debug.js
     *
     */
    
    var makeWebGLContext = function(canvas_name) {
    
        // get the canvas element to be used for drawing
        var canvas=$("#"+canvas_name).get(0);
        if(!canvas) { 
            throw "HTML element with id '"+canvas_name + "' not found"; 
            return null;
        };

        // get WebGL rendering context for canvas element
        var options = {alpha: true, depth: true, antialias:true};
        var gl = canvas.getContext("webgl", options) || 
                 canvas.getContext("experimental-webgl", options);
        if(!gl) {
            throw "could not create WebGL rendering context";
        };
        
        // create a debugging wrapper of the context object
        var throwOnGLError = function(err, funcName, args) {
            throw WebGLDebugUtils.glEnumToString(err) + " was caused by call to: " + funcName;
        };
        var gl=WebGLDebugUtils.makeDebugContext(gl, throwOnGLError);
        
        return gl;
    };
    
    /*
     * main program, to be called once the document has loaded 
     * and the DOM has been constructed
     * 
     */

    $(document).ready( (function() {
    
        // catch errors for debugging purposes 
        try {

            console.log("document ready - starting!");
            
            // create WebGL context object for the named canvas object
            var gl = makeWebGLContext("drawing_area");
                                        
            // a simple scene is an object with a few objects and a draw() method
            var MyRobotScene = function(gl, transformation) {

                // store the WebGL rendering context 
                this.gl = gl;  
                
                // create WebGL program using constant red color
                var prog_red = new Program(gl, shaders.vs_NoColor(), 
                                            shaders.fs_ConstantColor([0.7,0.3,0.2,1]) );
                var prog_blue = new Program(gl, shaders.vs_NoColor(), 
                                             shaders.fs_ConstantColor([0.5,0.3,0.5,1]) );
                                       
                // create WebGL program using per-vertex-color
                var prog_vertexColor = new Program(gl, shaders.vs_PerVertexColor(), 
                                                    shaders.fs_PerVertexColor() );
                                                    
                // please register all programs in this list
                this.programs = [prog_red, prog_blue, prog_vertexColor];
                                                    
                // create some objects to be drawn
                var cube   = new Cube(gl);
                var band   = new Band(gl, { radius: 0.5, height: 1.0, segments: 50 } );
                var triangle = new Triangle(gl);
                
                // dimensions
                var torsoSize      = [0.6, 1.0,  0.4 ];

                
                // skeleton for the torso - TODO connect shoulders and neck HERE
                var torso = new SceneNode("torso");
                
                // skin for the torso: a cube...
                var torsoSkin = new SceneNode("torso skin", [cube], prog_vertexColor);
                mat4.scale(torsoSkin.transformation, torsoSize );
                mat4.rotate(torsoSkin.transformation, Math.PI/2, [1,0,0] );
                
                // connect skeleton + skin
                torso.addObjects([torsoSkin]);
                                
                // an entire robot
                var robot1  = new SceneNode("robot1", [torso]);
                mat4.translate(robot1.transformation, [0,-0.5,0]);

                this.leftArm =  new Arm(this.gl, [-0.5* torsoSize[0], 0.2*torsoSize[1], 0.0], true, this.programs);
                this.rightArm = new Arm(this.gl, [ 0.5* torsoSize[0], 0.2*torsoSize[1], 0.0], false, this.programs);
                
                this.head = new Head(this.gl, [0.0, torsoSize[1]/2, 0.0], this.programs);
                
                robot1.addObjects([this.leftArm.getSceneNode(), this.rightArm.getSceneNode(), this.head.getSceneNode()]);
                
                // the world - this node is needed in the draw() method below!
                this.world  = new SceneNode("world", [robot1], prog_red); 
                
                // for the UI - this will be accessed directly by HtmlController
                this.drawOptions = {"Perspective": true};


                /*
                 *
                 * Method to rotate within a specified joint - called from HtmlController
                 *
                 */
                this.rotateJoint = function(joint, angle) {
                
                    //window.console.log("rotating " + joint + " by " + angle + " degrees." );
                    
                    // degrees to radians
                    angle = angle*Math.PI/180;
                    
                    // manipulate the right matrix, depending on the name of the joint
                    switch(joint) {
                        case "worldY": 
                            mat4.rotate(this.world.transformation, angle, [0,1,0]);
                            break;
                        case "worldX": 
                            mat4.rotate(this.world.transformation, angle, [1,0,0]);
                            break;
                        case "lShoulder":
                        	this.leftArm.rotateShoulder(angle);
                        	this.rightArm.rotateShoulder(-angle);
                        	break;
                        case "lElbow":
                        	this.leftArm.rotateElbow(angle);
                        	this.rightArm.rotateElbow(-angle);
                        	break;
                        case "lWrist":
                        	this.leftArm.rotateWrist(angle);
                        	this.rightArm.rotateWrist(-angle);
                        	break;
                        case "Head":
                        	this.head.rotate(angle);
                        	break;
                        default:
                            window.console.log("joint " + joint + " not implemented:");
                            break;
                    };
                    this.draw();
                }; // rotateJoint()
                
            }; // MyRobotScene constructor
            
            // the scene's draw method draws whatever the scene wants to draw
            MyRobotScene.prototype.draw = function() {
            
                // get aspect ratio of canvas
                var c = $("#drawing_area").get(0);
                var aspectRatio = c.width / c.height;
                
                // set camera's projection matrix in all programs
                var projection = this.drawOptions["Perspective"] ?
                                    mat4.perspective(45, aspectRatio, 0.01, 100) : 
                                    mat4.ortho(-aspectRatio, aspectRatio, -1,1, 0.01, 100);
                
                for(var i=0; i<this.programs.length; i++) {
                    var p = this.programs[i];
                    p.use();
                    p.setUniform("projectionMatrix", "mat4", projection);
                };
                                    
                // initial camera / initial model-view matrix
                var modelView = mat4.lookAt([0,0.5,3], [0,0,0], [0,1,0]);
                
                // shortcut
                var gl = this.gl;
                
                // clear color and depth buffers
                gl.clearColor(0.7, 0.7, 0.7, 1.0); 
                gl.clear(gl.COLOR_BUFFER_BIT | gl.DEPTH_BUFFER_BIT); 
                
                // enable depth testing, keep fragments with smaller depth values
                gl.enable(gl.DEPTH_TEST); 
                gl.depthFunc(gl.LESS);  
                
                // start drawing at the world's root node
                this.world.draw(gl,this.prog_vertexColor, modelView);

            }; // MyRobotScene draw()
            
            // create scene and start drawing
            var scene = new MyRobotScene(gl);
            scene.draw();
            
            // create an animation: rotate some joints
            var animation = new Animation( (function(t,deltaT) {
            
                this.customSpeed = this.customSpeed || 1;
                
                // speed  times deltaT
                var speed = deltaT/1000*this.customSpeed;
                
                // rotate around Y with relative speed 3
                scene.rotateJoint("Head", -10* Math.sin(t / 250));
            
                // redraw
                scene.draw();
                
            }));
            
            // create HTML controller that handles all the interaction of
            // HTML elements with the scene and the animation
            var controller = new HtmlController(scene,animation); 
        
        // end of try block
        } catch(err) {
            if($("#error")) {
                $('#error').text(err.message || err);
                $('#error').css('display', 'block');
            };
            window.console.log("exception: " + (err.message || err));;
            throw err;
        };
        
        
    })); // $(document).ready()
    
    
})); // define module
        

