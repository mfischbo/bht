package de.bht.fb6.cg1.colorconverter.view;

import static org.lwjgl.opengl.GL11.*;

/**
 * Class that visualizes the RGB color space as a cube
 * @author M. Fischboeck
 *
 */
public class RGBCube implements IDrawable {
	
	/** defines the space between each point */
	final private int 	stepSize;
	
	/** defines the size of the point */
	final private float	pointSize;
	
	/**
	 * Default constructor
	 */
	public RGBCube() {
		this.pointSize = 2.0f;
		this.stepSize =  5;
	}
	
	/**
	 * Constructor providing stepSize and pointSize
	 * @param pointSize The size of each point that is drawn
	 * @param stepSize The space between each point. Maximum value: 254
	 */
	public RGBCube(final float pointSize, final int stepSize) {
		if (stepSize > 254)
			throw new IllegalArgumentException("Step size exceeds maximus cube size of '254' pixels");
		
		this.stepSize = stepSize;
		this.pointSize = pointSize;
	}
	
	
	/**
	 * Renders the cube
	 */
	public void draw() {
		
		glHint(GL_POINT_SMOOTH_HINT, GL_NICEST);
		glPointSize(this.pointSize);

		// draw all R dependent points 
		for (int r=0; r < 256; r+=this.stepSize) {
			final float rCol = r / 255.0f;
			
			glBegin(GL_POINTS);
			glColor3f(rCol, 0, 0);
			glVertex3f(r, 0, 0);
			
			glColor3f(rCol, 255.0f, 0);
			glVertex3f(r, 255, 0);
			
			glColor3f(rCol, 0.0f, 255.0f);
			glVertex3f(r, 0, 255.0f);
			
			glColor3f(rCol, 255, 255);
			glVertex3f(r, 255, 255);
			glEnd();
		}
		
		// draw all G dependent points
		for (int g=0; g < 256; g+=this.stepSize) {
			
			final float gCol = g / 255.0f;
			
			glBegin(GL_POINTS);
			
			glColor3f(0, gCol, 0);
			glVertex3f(0, g, 0);
			
			glColor3f(255, gCol, 0);
			glVertex3f(255, g, 0);
			
			glColor3f(0, gCol, 255);
			glVertex3f(0, g, 255);
			
			glColor3f(255, gCol, 255);
			glVertex3f(255, g, 255);
			
			glEnd();
		}
		
		// draw all B dependent points
		for (int b=0; b < 256; b+=this.stepSize) {
			final float bCol = b / 255.0f;
			
			glBegin(GL_POINTS);
			
			glColor3f(0f, 0, bCol);
			glVertex3f(0, 0, b);
			
			glColor3f(255, 0, bCol);
			glVertex3f(255, 0, b);
			
			glColor3f(0, 255, bCol);
			glVertex3f(0, 255, b);
			
			glColor3f(255, 255, bCol);
			glVertex3f(255, 255, b);
			glEnd();
		}		
	}
}
