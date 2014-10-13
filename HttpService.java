package djohnson.git.networking.webServer;

import java.io.IOException;

/**
 * 
 * @author Jet2kus84
 * This class will service a client by method(GET,POST,etc)
 * 
 * 
			if(_request.getContentType().equals("applicaton/octet-stream")) {
								
						response.getWriter().print(
								"<html>"+
								"<body bgcolor=" + "\"0099FF\"" +">"+
								"<title> downloading </title>" +
								"<h1>Thank You for Downloading</h1>"+
								"<p>Your requested file is currently downloading</p>"+
								"</body>"+
								"</html>");
						
						response.getWriter().flush();
					}
 *
 */

public class HttpService implements Service, Runnable {

	@Override
	public void run() {
		
		if(request.getMethod().equals("GET")) {
			doGet(request, response);
		}
		else if(request.getMethod().equals("POST")) {
			doPost(request, response);
		}
		else if(request.getMethod().equals("PUT")) {
			doPut(request, response);
		}
		else if(request.getMethod().equals("DELETE")) {
			doDelete(request, response);
		}
		else if(request.getMethod().equals("HEAD")) {
			doHead(request, response);
		}
		else {
			response.getWriter().write("Not a valid request");
			return;
		}
		
		//close out all streams and client
		try {
		
			request.getReader().close();
			request.getInputStream().close();
			response.getWriter().close();
			response.getOutputStream().close();
			client.close();
					
		}catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void init() {
		
	}

	@Override
	public void doGet(HttpRequest _request, HttpResponse _response) {
		
		java.io.File file;
		
		//if home page is requested
		if(_request.getRequestURI().equals("/")) {
			file = new java.io.File(this.ROOT_DIRECTORY, DEFAULT);
		} 
		else {
			//obtain the resource requested
			file = new java.io.File(this.ROOT_DIRECTORY, _request.getRequestURI());
		}
		
		//get the length of the file
		int fileLength = (int)file.length();
		
		//create byte array for file data
		byte[] fileData = new byte[fileLength];
		
		//create file input with file requested, "try" uses autocloseable
		try(java.io.FileInputStream fileIn = new java.io.FileInputStream(file)) {
			
			fileIn.read(fileData);
			
			//send file contents to client
			_response.getOutputStream().write(fileData,0,fileLength); 
			_response.getOutputStream().flush();
		
			requestHandled = true;
			
		}catch (java.io.FileNotFoundException e) {
			requestHandled = false;
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	
		if(requestHandled) {
			System.out.println(request.getMethod()+" " +request.getRequestURI() +" "+
					request.getProtocol() +" request handled successfully ");
		}else {
			_response.getWriter().write(
					"<html>" +
					"<body bgcolor=" + "\"0099FF\"" +">" + 
							
					"<title>" + "404 File Not Found" + "</title>" +
					"<h1>Error 404</h1>" +
					"<p>Sorry, but file " + _request.getRequestURI() + " doesn't exist.</p>" +
					"<p><a href= " + "\"http://localhost:6940/djohnson/Post.txt\"" +">" + "View a random post" + "</a></p>" +
					
					"</body>" +
					"</html>");
			
			_response.getWriter().flush();
			//http://www.ign.com\
		}		
	}

	@Override
	public void doPost(HttpRequest _request, HttpResponse _response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doPut(HttpRequest _request, HttpResponse _response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doDelete(HttpRequest _request, HttpResponse _response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doHead(HttpRequest _request, HttpResponse _respone) {
		// TODO Auto-generated method stub
		
	}
	
	public HttpService(java.net.Socket _client) {
			
		this.client = _client;
		request = new HttpRequest(client);
		response = new HttpResponse(client);
		requestHandled = false;
		ROOT_DIRECTORY = "/users/Jet2kus84/desktop/ClientAccess/";
		DEFAULT = "web_develop/homepage.html";
	}
	
	private java.net.Socket client;
	private HttpRequest request;
	private HttpResponse response;
	private boolean requestHandled;
	private final String ROOT_DIRECTORY;
	private final String DEFAULT;
	
}
