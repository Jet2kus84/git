package djohnson.git.networking.webServer;

import java.net.ServerSocket;
import java.net.Socket;

public class HttpWebServer {

	public static void main(String[] args) {
		new HttpWebServer().runServer();
	}
	
	public void runServer() {
		
		System.out.println("Waiting for clients to connect ");
		
		try {
			//open a server connection
			server = new ServerSocket(PORT_NUMBER);
			
			//set server time out 
			server.setSoTimeout(TIME_OUT);
		
			while(true) {			
				
				//accept a client
				Socket client = server.accept();
				
				System.out.println("Client has connected");
			
				//service http request
				new Thread(new HttpService(client)).start();
			}
			
		} catch(java.net.SocketTimeoutException e) {
			
			System.out.println("Socket timed out");
			
		}catch (java.io.IOException e) {
			
		}
	}
	
	//IP address: 192.168.10.105
	//10.154.124.79
	private final int PORT_NUMBER = 6940;
	private final int TIME_OUT = 30000;
	private ServerSocket server;
	
}
