package de.bht.fb6.cg1.colorconverter.view;

import static org.lwjgl.opengl.GL11.*;

import de.bht.fb6.cg1.colorconverter.model.IColor;

/**
 * Class that states a object rendered by OpenGL to display the current color
 * within the {@link RGBCube}.
 * @author M. Fischboeck
 *
 */

public class ColorLense {

	/**
	 * Renders the color that is chosen as OpenGL Point.
	 * @param color The color to display.
	 */
	public static void draw(IColor color) {
		
		// calculate the values of the current color
		final int[] col = color.getParameters();
		glColor3f(col[0] / 255.0f, col[1] / 255.0f, col[2] / 255.0f);
		
		// calculate the size of the point
		final float pointSize = 25.0f - ((col[0] + col[2]) / 255.0f) * 10.0f;
		glPointSize(pointSize);
		
		// draw the point itself
		glBegin(GL_POINTS);
			glVertex3f(col[0], col[1], col[2]);
		glEnd();	
		glPointSize(1.0f);
	}
}
