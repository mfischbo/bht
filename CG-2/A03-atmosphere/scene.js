/*
 * WebGL core teaching framwork 
 * (C)opyright Hartmut Schirmacher, hschirmacher.beuth-hochschule.de 
 *
 * Module: Scene
 *
 * This module defines the scene to be drawn.
 *
 * A Scene object has a draw() method to draw itself, plus some
 * public attributes that may be used by animation functions etc:
 *
 * Scene.world:       the root node of the scene, of type SceneNode
 *
 * Scene.camera:      the camera to be used, consisting of two attributes:
 *                    camera.viewMatrix and camera.projectionMatrix.
 * 
 * Scene.drawOptions: array of strings defining on/off drawing options. 
 *                    this is potentially used by HtmlController to create
 *                    the corresponding check boxes in the HTML document.
 *
 */


/* requireJS module definition */
define(["jquery", "gl-matrix", 
        "program", "shaders", "scene_node", "texture", "light", "material",
        "models/parametric"], 
       (function($, glmatrix, 
                 Program, shaders, SceneNode, texture, light, material,
                 parametric ) {

    "use strict";
                                        
            // a simple scene is an object with a few objects and a draw() method
            var Scene = function(gl) {

                // store the WebGL rendering context 
                this.gl = gl;  
                
                // create WebGL program using constant blue color
                var prog_blue = new Program(gl, shaders("minimal_vs"), 
                                                shaders("frag_color_fs") );
                prog_blue.use();
                prog_blue.setUniform("fragColor", "vec4", [0.0, 0.0, 1.0, 1.0], false);
                
                // WebGL program for using phong illumination 
                var prog_phong = new Program(gl, shaders("phong_vs"), 
                                                 shaders("phong_fs")  );
                prog_phong.use();
                prog_phong.setUniform("ambientLight", "vec3", [0.4,0.4,0.4], false);
                
                
                
                // WebGL program for unsing the planet shader
                var prog_planet = new Program(gl, shaders("planet_vs"),
                								 shaders("planet_fs"));
                prog_planet.use();
                prog_planet.setUniform("ambientLight", "vec3", [0.4,0.4,0.4]);
                
                // load the texture for the earth
                this.texDaylight   = new texture.Texture2D(gl, "textures/earth_month04.jpg", true);
                this.texNightlight = new texture.Texture2D(gl, "textures/earth_at_night_2048-2.jpg", true);
                this.texBathymetry = new texture.Texture2D(gl, "textures/earth_bathymetry_4096.jpg", true);
                this.texClouds     = new texture.Texture2D(gl, "textures/earth_clouds_2048.jpg", true);

                var that = this;
                texture.onAllTexturesLoaded(function() {
                	console.log("[Scene] - setting textures on prog_planet");
                	prog_planet.use();
                	prog_planet.setTexture("earthDaylight", 0, that.texDaylight, true);
                	prog_planet.setTexture("earthNightlight", 1, that.texNightlight, true);
                	prog_planet.setTexture("earthBathymetry", 2, that.texBathymetry, true);
                	prog_planet.setTexture("earthClouds", 3, that.texClouds, true);
                });
                
                // register all programs in this list for setting the projection matrix later
                this.programs = [prog_blue, prog_phong, prog_planet];
                
                // light source 
                this.sun       = new light.DirectionalLight("light",  {"direction": [-1,0,0], "color": [1,1,1] }, [prog_phong, prog_planet] ); 
                this.sunNode   = new SceneNode("SunNode", [this.sun], prog_phong);
                
                // equator ring for orientation
                this.ringMaterial = new material.PhongMaterial("material", 
                                                                    {"ambient":   [0.2,0.2,0.2],
                                                                     "diffuse":   [0.7,0.7,0.7],
                                                                     "specular":  [0.4,0.4,0.4],
                                                                     "shininess": 80
                                                                     });
                this.ringGeometry = new parametric.Torus(gl, 1.7, 0.01, {"uSegments":80, "vSegments":40});
                this.ringNode     = new SceneNode("EquatorNode", [this.ringMaterial, this.ringGeometry], prog_phong);
                console.log("ring prog: " + this.ringNode.program);

                // the poles are modeled on the Z axis, so swap z and y axes  
                mat4.rotate(this.ringNode.transformation, Math.PI/2.0, [1,0,0]);
                
                // create the earth node
                this.earthGeom = new parametric.Sphere(gl, 1.5, {"uSegments":80, "vSegments":40, "wireframe":false});
                this.earthMat  = new material.PhongMaterial("material", {"ambient":	[0.1,0.1,0.1],
                														 "diffuse": [6.0,6.0,6.0],
                														 "specular": [1.0,1.0,1.0],
                														 "shininess": 20
                													});
                this.earthNode = new SceneNode("EarthNode", [this.earthMat, this.earthGeom], prog_planet);
                mat4.rotate(this.earthNode.transformation, 90 * (Math.PI/180), [1,0,0]);
                
                
                // create a GridNode for the wireframe of the earth
                this.gridGeom = new parametric.Sphere(gl, 1.5, {"uSegments":80, "vSegments":40, "wireframe":true});
                this.gridNode = new SceneNode("GridNode", [this.gridGeom], prog_blue);                
                this.earthNode.addObjects([this.gridNode]);
                
                // the world node - this is potentially going to be accessed from outside
                this.world  = new SceneNode("world", [this.sunNode, this.ringNode,
                                                      this.earthNode], prog_planet); 
                
                // initial camera parameters
                var c = gl.canvas;
                var aspectRatio = c.width / c.height;
                this.camera = {};
                this.camera.viewMatrix = mat4.lookAt([0,0.5,4], [0,0,0], [0,1,0]);
                this.camera.projectionMatrix = mat4.perspective(45, aspectRatio, 0.01, 100);
                
                // for the UI - this will be accessed directly by HtmlController
                this.drawOptions = { "Planet": false, "Ring": true, "Wireframe":false, "NightTexture":false, "Clouds":false};
                
            }; // Scene constructor
            
            // draw the scene, starting at the root node
            Scene.prototype.draw = function() {
            
                // shortcut
                var gl = this.gl;
                
                // switch grid on/off
                this.ringNode.visible = this.drawOptions["Ring"];
                this.earthNode.visible = this.drawOptions["Planet"];
                this.gridNode.visible = this.drawOptions["Wireframe"];
                
                // set camera's projection matrix in all programs
                for(var i=0; i<this.programs.length; i++) {
                    var p = this.programs[i];
                    p.use();
                    p.setUniform("projectionMatrix", "mat4", this.camera.projectionMatrix);
                };
                
                // set night texture boolean in planet fragment shader
                this.programs[2].use();
                this.programs[2].setUniform("enableNightTex", "float", this.drawOptions['NightTexture']? 1.0:0.0);
                this.programs[2].setUniform("enableCloudTex", "float", this.drawOptions['Clouds']? 1.0:0.0);
                
                // initially set model-view matrix to the camera's view matrix
                var modelView = this.camera.viewMatrix;
                
                // enable depth testing, keep fragments with smaller depth values
                gl.enable(gl.DEPTH_TEST); 
                gl.depthFunc(gl.LESS);  
                                
                // clear color and depth buffers
                gl.clearColor(0.0, 0.0, 0.0, 1.0); 
                gl.clear(gl.COLOR_BUFFER_BIT | gl.DEPTH_BUFFER_BIT); 
                
                // start drawing with the root node
                this.world.draw(gl, null, modelView);

            }; // Scene draw()
            
        return Scene;
    
})); // define module
        
