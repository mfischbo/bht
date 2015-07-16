package de.bht.fb6.cg1.colorconverter.view;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.Display;

import de.bht.fb6.cg1.colorconverter.model.IColor;

/**
 * Container for objects that should be rendered by OpenGL
 * @author M. Fischboeck
 *
 */
public class OpenGlView {
	
	/**
	 * Contains all elements that are rendered with OpenGL
	 */
	final private List<IDrawable> objects;
	
	/**
	 * Default constructor
	 */
	public OpenGlView() {
		this.objects = new ArrayList<IDrawable>();
	}
	
	/**
	 * Adds a new object to the list of rendered objects
	 * @param object The object to be rendered
	 */
	public void register(IDrawable object) {
		this.objects.add(object);
	}
	
	/**
	 * Removes an object from the list of rendered objects
	 * @param object The object to be removed
	 */
	public void unregister(IDrawable object) {
		this.objects.remove(object);
	}
	
	/**
	 * Initializes OpenGL
	 */
	public static void init() {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0.0, 640.0, 0.0, 400.0, -400, 400);
		glMatrixMode(GL_MODELVIEW);
		glClear(GL_COLOR_BUFFER_BIT);
		Display.update();
	}
	
	
	/**
	 * Renders all registered objects
	 * @param color The current reference color
	 */
	public void renderFrame(IColor color) {

		glShadeModel (GL_SMOOTH);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
		final float bgCol = 0.7f;
		glClearColor(bgCol, bgCol, bgCol, 0.0f);
		
		glLoadIdentity();
		glTranslatef(320.0f, 50.0f, 0.0f);
		glRotatef(10, 1.0f, 0, 0);
		glRotatef(125, 0.0f, 1.0f, 0.0f);	
		
		for (final IDrawable d : objects) {
			d.draw();
		}
		
		ColorLense.draw(color);
		Display.update();
	}
}
