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
			var Arm = function(gl, origin, left, programs) {

				// create some objects to be drawn
				var cube = new Cube(gl);
				var band = new Band(gl, {
					radius : 0.5,
					height : 1.0,
					segments : 50
				});
				var triangle = new Triangle(gl);
				
				this.elbowRotation = 0.0;
				this.shoulderRotation = 0.0;
				

				var upperArmSize = [ 0.1, 0.4, 0.1 ];
				var lowerArmSize = [ 0.1, 0.3, 0.1 ];
				var elbowSize = [ 0.098, 0.098, 0.098 ];
				var shoulderSize = [ 0.098, 0.098, 0.098 ];
				var wristSize  = [0.060, 0.060, 0.060];
				var handSize   = [0.1, 0.1, 0.1];
				
				// create the scene node for the arm and translate to origin
				this.arm = new SceneNode("Arm");
				mat4.translate(this.arm.transformation,[origin[0], origin[1], origin[2]]);
				
				
                if (!left) {
                	mat4.rotate(this.arm.transformation, Math.PI, [0, 1, 0]);
                }
                
				
				// create a hand
                this.hand = new SceneNode("hand");
                var handSkin = new SceneNode("hand skin", [cube], programs[2]);
                mat4.translate(this.hand.transformation,
                		[0.0, -1.5*wristSize[1], 0.0]);
                mat4.scale(handSkin.transformation, handSize);
                mat4.rotate(handSkin.transformation, Math.PI/2, [0, 2, 0]);
                this.hand.addObjects([handSkin]);
				
				// create a wrist
                this.wrist = new SceneNode("wrist", [this.hand]);
                var wristSkin = new SceneNode("wrist skin", [band], programs[0]);
                mat4.translate(this.wrist.transformation,
                		[0.0, -1*lowerArmSize[1]/2, 0.0]);
                mat4.scale(wristSkin.transformation, wristSize);
                this.wrist.addObjects([wristSkin]);
				
				
				// create the lower arm node
                this.leftLowerArm = new SceneNode("left Lower Arm", [this.wrist]);
                var leftLowerArmSkin = new SceneNode("leftLowerArmSkin", [cube], programs[1]);
                mat4.translate(this.leftLowerArm.transformation, 
                		[0.0, -2 * elbowSize[1], 0.0]);
                
                mat4.scale(leftLowerArmSkin.transformation, lowerArmSize);
                this.leftLowerArm.addObjects([leftLowerArmSkin]);
                
				
				 // create the elbow node
                this.leftElbow = new SceneNode("leftElbow", [this.leftLowerArm]);
                mat4.translate(this.leftElbow.transformation, 
                		[0.0, -1 * upperArmSize[1]/2, 0.0]);
                
                var leftElbowSkin = new SceneNode("Left Elbow Skin", [band], programs[0]);
                mat4.rotate(leftElbowSkin.transformation, Math.PI/2, [0, 0, 1]);
                mat4.scale(leftElbowSkin.transformation, elbowSize);
                this.leftElbow.addObjects([leftElbowSkin]);
                
				
				 // create the upper arm node
                this.leftUpperArm = new SceneNode("leftUpperArm", [this.leftElbow]);
                this.leftUpperArmSkin = new SceneNode("leftUpperArmSkin", [cube], programs[1]);
                
                mat4.translate(this.leftUpperArm.transformation, 
                		[-upperArmSize[0]/2 - shoulderSize[0]/10, -upperArmSize[1]/2, 0.0]);
                mat4.scale(this.leftUpperArmSkin.transformation, upperArmSize);
                this.leftUpperArm.addObjects([this.leftUpperArmSkin]);    
                
				
				// create the shoulder for the arm
				this.leftShoulder = new SceneNode("leftShoulder", [this.leftUpperArm]);
                mat4.translate(this.leftShoulder.transformation, 
                		[-1* shoulderSize[0]/2, origin[1], origin[2]]);
				this.leftShoulderSkin = new SceneNode("leftShoulderSkin", [band], programs[0]);
                mat4.scale(this.leftShoulderSkin.transformation, shoulderSize);      
                mat4.rotate(this.leftShoulderSkin.transformation, Math.PI/2, [0, 0, 1]);
                this.leftShoulder.addObjects([this.leftShoulderSkin]);

                
                this.arm.addObjects([this.leftShoulder]);
			};
			
			
			Arm.prototype.rotateShoulder = function(angle) {
				var rot = this.shoulderRotation + angle;
				if (rot > -1* Math.PI/2 && rot < Math.PI/2) {
					mat4.rotate(this.leftShoulder.transformation, angle, [1, 0, 0]);
					this.shoulderRotation = rot;
				}
			};
			
			
			Arm.prototype.rotateElbow = function(angle) {
				
				var rot = this.elbowRotation + angle;
				if (rot > -1*Math.PI/2 && rot < Math.PI/2) {
					this.elbowRotation = rot;
					mat4.rotate(this.leftElbow.transformation, angle, [1, 0, 0]);
				}
			};
			
			Arm.prototype.rotateWrist = function(angle) { 
				mat4.rotate(this.wrist.transformation,2*angle, [0, 1, 0]);
			};
			

			Arm.prototype.getSceneNode = function() {
				return this.arm;
			}

			return Arm;

		})); // define
