package de.bht.fb6.cg1.colorconverter;

import de.bht.fb6.cg1.colorconverter.view.ApplicationWindow;

/**
 * Main application entrance point.
 * This class has no useful purpose except creating the actual application.
 * 
 * @author M. Fischboeck
 *
 */

public class Main {

	/**
	 * Main method for the application
	 * @param args - Unused
	 */
	public static void main(String[] args) {
		
		final ApplicationWindow w = new ApplicationWindow();
		w.mainLoop();
	}
}