package djohnson.git.networking.webServer;

import djohnson.git.networking.webServer.serverRequest.ServerRequest;

import java.net.Socket;

/**
 * 
 * @author Jet2kus84
 * 
 * This class will obtain client request and break
 * the request in to pieces of info. This can be 
 * consider front-end development.
 */
public class HttpRequest extends ServerRequest {
	
	/**
	 * @param _client socket for communication with client
	 */
	public HttpRequest(Socket _client) {
		super(_client);
		init();
	}
	
	/**
	 *  retrieve and initialize http request info
	 */
	private void init() {
		
		try {
	
			//set input streams
			setReader(client.getInputStream());
			setInputStream(client.getInputStream());
					
			//get client request	
			String request = getReader().readLine();
			
			if(request == null) {
				//use default
				request = "GET /djohnson/index.html HTTP/1.1";
			}
			
			java.util.StringTokenizer parse = 
					new java.util.StringTokenizer(request);
			
			//get and set the method
			setMethod(parse.nextToken());
			
			//get the resource requested
			String URI = parse.nextToken();
			
			//get and set the mime type of the resource
			setContentType(contentType(URI));
			
			//set the resource being requested
			setRequestURI(URI);
			
			//get the protocol name and verison
			setProtocol(parse.nextToken());
					
		} catch (java.io.IOException e) {
			System.err.println("System error" + e);
		} 
	}
	
	/**
	 * 
	 * @param uri resource being requested
	 * @return mime type
	 */
	private String contentType(String uri) {
		if(uri.endsWith(".html") || uri.endsWith(".htm")) {
			return "text/html";
		}
		else if(uri.endsWith(".jpg") || uri.endsWith(".jpeg")) {
			return "image/jpeg";
		}
		else if(uri.endsWith(".png")) {
			return "image/png";
		}
		else if (uri.endsWith(".gif")) {
			return "image/gif";
		}
		else if (uri.endsWith(".ico")) {
			return "file type not supported";
		}
		else if(uri.endsWith(".jar") || 
				uri.endsWith(".class") || uri.endsWith(".zip")) {
			return "applicaton/octet-stream";
		}
		else if(uri.endsWith("/")) {
			return "no file requested";
		}
		else 
			return "text/plain";
	}
}
