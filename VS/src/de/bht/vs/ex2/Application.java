package de.bht.vs.ex2;

import de.bht.vs.ex2.http.HttpServer;

/**
 * Main class of the phone book server application.
 * @author M. Fischboeck
 *
 */
public class Application {
	
	/**
	 * Main routine for the application
	 * @param args 
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {

		// print the applications greeter
		System.out.println("**********************************************");
		System.out.println("* Welcome to the Beuth Phone Dictionary      *");
		System.out.println("* Manual:                                    *");
		System.out.println("* Enter a name or number or both             *");
		System.out.println("**********************************************");
		
		// Start the server
		HttpServer server = new HttpServer(8080);
		server.start();

	}
}
