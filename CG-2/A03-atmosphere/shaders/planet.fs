/*
 * WebGL core teaching framwork 
 * (C)opyright Hartmut Schirmacher, hschirmacher.beuth-hochschule.de 
 *
 * Fragment Shader: phong
 *
 * expects position and normal vectors in eye coordinates per vertex;
 * expects uniforms for ambient light, directional light, and phong material.
 * 
 *
 */

precision mediump float;

// position and normal in eye coordinates
varying vec4  ecPosition;
varying vec3  ecNormal;

// transformation matrices
uniform mat4  modelViewMatrix;
uniform mat4  projectionMatrix;

// Ambient Light
uniform vec3 ambientLight;

// Material
struct PhongMaterial {
    vec3  ambient;
    vec3  diffuse;
    vec3  specular;
    float shininess;
};
uniform PhongMaterial material;

// Light Source Data for a directional light
struct LightSource {

    int  type;
    vec3 direction;
    vec3 color;
    bool on;
    
} ;
uniform LightSource light;

// Texures
uniform sampler2D earthDaylight;
uniform sampler2D earthNightlight;
uniform sampler2D earthBathymetry;
uniform sampler2D earthClouds;
varying vec2 texCoords;

// Enable Night Texture
varying float enableNight;
varying float enableClouds;


/**
 * Inverts the given color and darkens it by offset
 */
vec3 invertColor(vec3 tex, float offset) {

	vec3 retval;
	retval.r = 1.0 - tex.r - offset;
	retval.g = 1.0 - tex.g - offset;
	retval.b = 1.0 - tex.b - offset;
	return retval;
}

/*

 Calculate surface color based on Phong illumination model.
 - pos:  position of point on surface, in eye coordinates
 - n:    surface normal at pos
 - v:    direction pointing towards the viewer, in eye coordinates
 + assuming directional light
 
 */
vec3 phong(vec3 pos, vec3 n, vec3 v, LightSource light, PhongMaterial material) {


    // ambient part
    vec3 ambient = material.ambient * ambientLight;

    // back face towards viewer?
    float ndotv = dot(n,v);
    if(ndotv<0.0) {
    	return vec3(0,0,0);
    }


	// load textures for night and day and blend them with clouds if required
    vec3 dTex = texture2D(earthDaylight, texCoords).rgb;
	vec3 nTex = texture2D(earthNightlight, texCoords).rgb;
	vec3 cTex = texture2D(earthClouds, texCoords).rgb;
	
	if (enableClouds > 0.2) {
		dTex = mix(dTex, cTex, cTex.r);
		nTex = mix(nTex, cTex, cTex.r * 0.4);
	}

    // vector from light to current point
    vec3 l = normalize(light.direction);
    
    // cos of angle between light and surface. 0 = light behind surface
    float ndotl = dot(n,-l);
    if(ndotl<=0.0) {    	
    	if (enableNight > 0.2)
        	return nTex + ambient;
    	else
    		return ambient;
    }
    
	// load the glossy map texture
	vec3 bTex = texture2D(earthBathymetry, texCoords).rgb;

    // reflected light direction = perfect reflection direction
    vec3 r = reflect(l,n);

    // angle between reflection dir and viewing dir
    float rdotv = max( dot(r,v), 0.0);
    
    // specular contribution
    vec3 specular = bTex.r * material.specular * light.color * pow(rdotv, material.shininess);
    
	// interpolate between night and day
    if (ndotl > 0.0 && ndotl < 0.35) {
    	if (enableNight > 0.2)
    		return mix(nTex + ambient, ambient + dTex + specular, ndotl / 0.35);
    	else
    		return mix(ambient, ambient + dTex + specular, ndotl / 0.35);
    }
    
    // everything else is bright lighted day
    return ambient + dTex + specular;   
}




void main() {
    
    // normalize normal after projection
    vec3 normalEC = normalize(ecNormal);
    
    // do we use a perspective or an orthogonal projection matrix?
    bool usePerspective = projectionMatrix[2][3] != 0.0;
    
    // for perspective mode, the viewing direction (in eye coords) points
    // from the vertex to the origin (0,0,0) --> use -ecPosition as direction.
    // for orthogonal mode, the viewing direction is simply (0,0,1)
    vec3 viewdirEC = usePerspective? normalize(-ecPosition.xyz) : vec3(0,0,1);
    
    // calculate color using phong illumination
    vec3 color = phong( ecPosition.xyz, normalEC, viewdirEC,
                        light, material );
    
    // set fragment color
    gl_FragColor = vec4(color, 1.0);
}
