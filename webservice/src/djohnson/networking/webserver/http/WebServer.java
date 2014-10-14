package djohnson.git.networking.webServer.http;

import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;
import java.net.SocketTimeoutException;

public class WebServer {

	public static void main(String[] args) {
		new WebServer().runWebServer();
	}
	
	public void runWebServer() {
		
		System.out.println("Waiting for clients to connect");
		
		try {
			server = new ServerSocket(PORT_NUMBER);
			server.setSoTimeout(TIME_OUT);
			
			while(true) {
				
				Socket client = server.accept();
				
				System.out.println("Client[" + ++numOfClients + "]" + " connected");
				
				new Thread(new WebWorker(client)).start();
			}
			
		} catch(SocketTimeoutException e) {
			System.out.println("Socket has timed out.");
			
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			System.exit(0);
			
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public WebServer() {
		
		PORT_NUMBER = 4567;
		server = null;
		numOfClients = 0;
		TIME_OUT = 20000;
	}
	
	private final int PORT_NUMBER;
	private final int TIME_OUT;
	private ServerSocket server;
	private int numOfClients;

}
