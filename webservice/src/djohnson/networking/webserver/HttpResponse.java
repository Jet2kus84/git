package djohnson.git.networking.webServer;

import djohnson.git.networking.webServer.serverResponse.ServerResponse;

import java.net.Socket;

/**
 * 
 * @author Jet2kus84
 * 
 * This class will mediate the client and the 
 * application. This class will provide the 
 * application with the required components/parameters.
 */
public class HttpResponse extends ServerResponse {

	public HttpResponse(Socket _client) {
		super(_client);
		init();
	}
	
	private void init() {
		try {
			
			//set input streams
			this.setWriter(client.getOutputStream());
			this.setServerOutputStream(client.getOutputStream());
			
		} catch (java.io.IOException e) {
                    this.getWriter().write("Issue occured when trying to"
                            + " obtain output stream.");
		}
	}
	
}
