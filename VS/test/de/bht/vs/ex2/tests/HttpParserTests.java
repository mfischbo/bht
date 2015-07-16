/**
 * 
 */
package de.bht.vs.ex2.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.bht.vs.ex2.http.HttpParser;
import de.bht.vs.ex2.http.HttpRequest;
import de.bht.vs.ex2.http.HttpRequest.HttpMethod;

/**
 * @author M. Fischboeck
 *
 */
public class HttpParserTests {

	
	@Test
	public void acceptsBasicRequest() throws Exception {
		String req = "GET / HTTP/1.1";
		HttpRequest request = HttpParser.parse(req);
		assertTrue(request.isValid());
	}
	
	
	@Test
	public void acceptsPathRequest() throws Exception {
		HttpRequest request = HttpParser.parse("GET /foo/bar/ HTTP/1.1");
		assertTrue(request.isValid());
		assertEquals("/foo/bar", request.getPath());
	}
	
	@Test
	public void acceptsBasfileRequest() throws Exception {
		HttpRequest request = HttpParser.parse("GET /foo/bar/index.html HTTP/1.1");
		assertTrue(request.isValid());
		assertEquals(HttpMethod.GET, request.getMethod());
		assertEquals("/foo/bar", request.getPath());
		assertEquals("index.html", request.getBasefile());
	}
	
	@Test
	public void acceptsQueryRequestWithoutBasefile() throws Exception {
		HttpRequest request = HttpParser.parse("GET /foo/bar/?foo=foo&bar=bar HTTP/1.1");
		assertTrue(request.isValid());
		assertEquals("/foo/bar", request.getPath());
		assertEquals(2, request.getParameters().keySet().size());
		assertEquals("foo", request.getParameters().get("foo"));
		assertEquals("bar", request.getParameters().get("bar"));
	}
	
	@Test
	public void acceptsQueryRequestWithBasefile() throws Exception {
		HttpRequest request = HttpParser.parse("GET /foo/bar/index.html?foo=foo&bar=bar HTTP/1.1");
		assertTrue(request.isValid());
		assertEquals("/foo/bar", request.getPath());
		assertEquals(2, request.getParameters().keySet().size());
		assertEquals("foo", request.getParameters().get("foo"));
		assertEquals("bar", request.getParameters().get("bar"));
	}
	
	@Test
	public void acceptsHttpHeaders() throws Exception {
		HttpRequest request = HttpParser.parse("POST /foo/bar/index.html?foo=bar HTTP/1.1\nAccept:text/html\nUser-Agent:Java");
		assertTrue(request.isValid());
		
		assertEquals(HttpMethod.POST, request.getMethod());
		assertEquals("/foo/bar", request.getPath());
		assertEquals("index.html", request.getBasefile());
		
		assertEquals(1, request.getParameters().keySet().size());
		assertEquals("bar", request.getParameters().get("foo"));
		
		assertEquals(2, request.getHeaders().keySet().size());
		assertEquals("text/html", request.getHeaders().get("Accept"));
		assertEquals("Java", request.getHeaders().get("User-Agent"));
		
		assertEquals("HTTP/1.1", request.getVersion());
	}
	
}
