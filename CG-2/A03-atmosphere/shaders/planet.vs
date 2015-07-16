/*
 * WebGL core teaching framwork 
 * (C)opyright Hartmut Schirmacher, hschirmacher.beuth-hochschule.de 
 *
 * Vertex Shader: phong
 *
 * This shader expects a position and normal vector per vertex,
 * and model-view, projection and normal matrices.
 *
 * it transforms the position and normal to eye coordinates and
 * passes them to the fragment shader as varying variables; 
 * it also transforms the position to clip coordinates for the
 * needs of the pipeline.
 *
 */


attribute vec3 vertexPosition;
attribute vec3 vertexNormal;
attribute vec2 vertexTexCoords;

uniform mat4 modelViewMatrix;
uniform mat4 projectionMatrix;
uniform mat3 normalMatrix;
uniform float enableNightTex;
uniform float enableCloudTex;

varying vec4 ecPosition;
varying vec3 ecNormal;

varying vec2 texCoords;
varying float enableNight;
varying float enableClouds;

void main() {
    
    // transform vertex position and normal into eye coordinates
    // for lighting calculations
    ecPosition   = modelViewMatrix * vec4(vertexPosition,1.0);
    ecNormal     = normalize(normalMatrix*vertexNormal);
    
    // set the fragment position in clip coordinates
    gl_Position  = projectionMatrix * ecPosition;
    
    // set the texture coordinates to be used in the fragment shader
    texCoords = vertexTexCoords;
    
    // set the boolean value for enabled night textures in the fs
    enableNight = enableNightTex;
    enableClouds = enableCloudTex;
}

