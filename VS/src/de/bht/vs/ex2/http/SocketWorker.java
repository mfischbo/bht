/**
 * 
 */
package de.bht.vs.ex2.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import de.bht.vs.ex2.PhoneBook;
import de.bht.vs.ex2.Searcher;
import de.bht.vs.ex2.model.Entry;

/**
 * The socket worker serves each incoming request as a single running thread.
 * This ensures that multiple requests can be served at the same time.
 * @author M. Fischboeck
 *
 */
public class SocketWorker implements Runnable {

	/* The client socket to read and write to */
	private Socket						_socket;
	
	/* The input as read from the socket */
	private String						_socketInput;
	
	/* The request representation of _socketInput */
	private HttpRequest					_request;
	
	/* The history of incoming requests */
	private Map<String, HttpRequest>	_history;
	
	
	/**
	 * Constructor with a client socket
	 * @param s The client socket to read from and write to
	 * @param history The history of requests, as served by the server
	 */
	public SocketWorker(Socket s, Map<String, HttpRequest> history) {
		this._socket = s;
		this._history = history;

		// read the input from the socket
		try {
			if (_socket.isConnected()) {
				System.out.println("Reading request from " + _socket.getRemoteSocketAddress());
				InputStream in = _socket.getInputStream();
				byte[] buffer = new byte[1024];
				StringBuffer b = new StringBuffer();
				boolean reading = true;
				while (reading) {

					in.read(buffer);
					
					if (buffer[0] == 0)
						break;
					
					// terminate reading on empty line with CR/LF characters
					if (buffer[1023] == 0) {
						// the end of the buffer contains NUL, indicating the request has finished
						for (int i=0; i < buffer.length-3; i++) {
							if (buffer[i] == 13 && buffer[i+1] == 10 && buffer[i+2] == 13 && buffer[i+3] == 10) {
								reading = false;
								break;
							}
						}
					}
					b = b.append(new String(buffer));
					buffer = new byte[1024];
				}
				this._socketInput = b.toString().trim();
				System.out.println("Done reading request. Received : " + this._socketInput.length() + " bytes");
				
				_request = HttpParser.parse(this._socketInput);
				System.out.println(_request.toString());
			}
		} catch (Exception ex) {
			System.err.println("Unable to read from socket. Cause: " + ex.getMessage());
		}
	}


	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		
		if (this._request != null && _request.isValid()) {
		
			// Serves the shutdown request
			if (this._request.getPath().equals("/server/shutdown")) {
				System.out.println("Received shutdown signal.");
				try {
					_socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					System.out.println("Server has been shut down successfully");
					System.exit(0);
				}
			}
			
			
			// Request for the form page (index.html)
			if (_request.getPath().equals("/") && _request.getBasefile().equals("index.html")) {
				String html = readResource(_request.getBasefile());
				write(200, html);
				return;
			}
			
			// Request for the actual search (search.html)
			if (_request.getPath().equals("/") && _request.getBasefile().equals("search.html")) {
				List<Entry> result = new LinkedList<Entry>();
				
				String html = readResource(_request.getBasefile());
				String name = _request.getParameters().get("name");
				String number  = _request.getParameters().get("number");
				
				// Search on both fields
				if (name != null && number != null) {
					Entry e = new Entry(name, number);
					
					Searcher s1 = new Searcher(e, new PhoneBook(), Searcher.SearchOn.Name);
					Searcher s2 = new Searcher(e, new PhoneBook(), Searcher.SearchOn.Number);
					
					Thread t1 = new Thread(s1, "searchers");
					Thread t2 = new Thread(s2, "searchers");
					t1.start();
					t2.start();
					try {
						t1.join();
						t2.join();
						
						result = s1.getResult();
						result.addAll(s2.getResult());
						
					} catch (Exception ex) {
						System.out.println("Error during the search. Cause: " + ex.getMessage());
					}
 				}
				
				// Search on Name field
				if (name != null && number == null) {
					Entry e = new Entry(name, null);
					Searcher s1 = new Searcher(e, new PhoneBook(), Searcher.SearchOn.Name);
					Thread   t1 = new Thread(s1, "searchers");
					t1.start();
					try {
						t1.join();
						result = s1.getResult();
					} catch (Exception ex) {
						System.out.println("Error during search on name field. Cause: "+ ex.getMessage());
					}
				}
				
				// Search on number field
				if (name == null && number != null) {
					Entry e = new Entry(null, number);
					Searcher s1 = new Searcher(e, new PhoneBook(), Searcher.SearchOn.Number);
					Thread t1 = new Thread(s1, "searchers");
					t1.start();
					try {
						t1.join();
						result = s1.getResult();
					} catch (Exception ex) {
						System.out.println("Error during search on number field. Cause: " + ex.getMessage());
					}
				}
				
				// If no data has been provided, return empty search
				if (name == null && number == null) {
					html = html.replaceAll("\\{searchResult\\}", "No search has been made, since request was empty.");
					write(200, html);
					return;
				}
				
				// replace the output in the template
				if (result.size() > 0) {
					StringBuffer b = new StringBuffer();
					for (Entry e : result) 
						b.append(e.getName() + "   " + e.getNumber() + "<br/>");
					html = html.replaceAll("\\{searchResult\\}", b.toString());
					
				} else {
					html = html.replaceAll("\\{searchResult\\}", "");
				}
				
				write(200, html);
				return;
			}
			
			// no matching file could be found. Return 404
			String html = readResource("404.html");
			html = html.replaceAll("\\{reqFile\\}", _request.getPath() +"/"+ _request.getBasefile() + _request.getQueryString());
			write(404, html);
			
		}
		
		
		// at this point all work is done. Close the socket
		try {
			System.out.println("Successfully transmitted message. Closing connection.");
			_socket.close();
		} catch (Exception ex) {
			System.out.println("Unable to close connection. Cause: " + ex.getMessage());
		}
	}
	
	
	/**
	 * Reads a resource from the classpath directory de.bht.vs.ex2.resources
	 * @param filename The filename of the resource
	 * @return The raw resource as string
	 */
	private String readResource(String filename) {
		
		try {
			InputStream in = getClass().getResourceAsStream("/de/bht/vs/ex2/resources/"+filename);
			StringBuffer b = new StringBuffer();
			byte[] buffer = new byte[1024];
			while (in.read(buffer) > -1) {
				b.append(new String(buffer));
				buffer = new byte[1024];
			}
			return b.toString();
			
		} catch (Exception ex) {
			System.err.println("Unable to read resource : " + filename + ". Cause: " + ex.getMessage());
		}
		return "";
	}
	
	
	/**
	 * Wraps the given content in a {@link HttpResponse} and writes it to the client.
	 * @param status The status of the operation
	 * @param output The content of the operation
	 */
	private void write(int status, String output) {
		try {
			// try to set the backref
			String host = _request.getHeaders().get("Host");
			if (host != null && host.length() > 0 && _history.containsKey(host)) {
			
				String backref = _history.get(host).getPath();
				backref += 	     _history.get(host).getBasefile();
				backref +=		 _history.get(host).getQueryString();
				output = output.replaceAll("\\{backRef\\}", backref);
			} else {
				output = output.replaceAll("\\{backRef\\}", "#");
			}
			
			// create the HttpResponse and write it to the client
			HttpResponse response = new HttpResponse();
			response.setStatus(status);
			response.setContent(output.trim());
			
			String message = response.serialize();
			System.out.println("Writing " + message.length() + " bytes to client.");
			System.out.println("Header: ");
			System.out.println(response.getHeader());
			_socket.getOutputStream().write(message.getBytes());
			
			// store the currently served request in the history
			this._history.put(_request.getHeaders().get("Host"), _request);
		} catch (Exception ex) {
			System.out.println("Unable to write to client. Cause: " + ex.getMessage());
		}
	}
}
