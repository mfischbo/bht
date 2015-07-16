define([ "util", "gl-matrix", "scene_node", "models/band", "models/cube", "models/triangle" ],
		(function(util, dummy, SceneNode, Band, Cube, Triangle) {

			/**
			 * Constructs a new Arm
			 * 
			 * @param origin
			 *            The origin of the arm as [x,y,z] coordinates
			 * @param left
			 *            True, if the arm is the left arm, otherwise it's the
			 *            right arm
			 */
			var Head = function(gl, origin, programs) {

				// create some objects to be drawn
				var cube = new Cube(gl);
				var band = new Band(gl, {
					radius : 0.5,
					height : 1.0,
					segments : 50
				});
				var triangle = new Triangle(gl);
				
				var headSize = [0.4, 0.4, 0.4];
				var neckSize = [0.2, 0.2, 0.2];
				
				this.head = new SceneNode("head");
				mat4.translate(this.head.transformation, [origin[0], origin[1], origin[2]]);
				
				
				// create a head
				this.cHead = new SceneNode("Head part");
				var cHeadSkin = new SceneNode("head part skin", [cube], programs[2]);
				mat4.translate(this.cHead.transformation, [0.0, neckSize[1], 0.0]);
				mat4.scale(cHeadSkin.transformation, headSize);
				this.cHead.addObjects([cHeadSkin]);
				
				// create the neck
				this.neck = new SceneNode("Neck", [this.cHead]);
				var neckSkin = new SceneNode("neck skin", [band], programs[1]);
				mat4.translate(this.neck.transformation, [0.0, neckSize[1]/2, 0.0]);
				mat4.scale(neckSkin.transformation, neckSize);
				this.neck.addObjects([neckSkin]);
				
				this.head.addObjects([this.neck]);
			};
			
			
			Head.prototype.getSceneNode = function() {
				return this.head;
			};
			
			
			Head.prototype.rotate = function(angle) {
				mat4.rotate(this.neck.transformation, angle, [0, 1, 0]);
			}
			
			return Head;

		})); // define
