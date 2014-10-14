package djohnson.git.networking.webServer.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class WebClient {

	//run program
	public static void main(String[] args) {
		WebClient.Instance().runClient(args); 
	}
		
	public void runClient(String[] args) {
		
		if(args.length < 1) {
			//Default name is user doesn't type a name
			serverName = "localhost";
		}
		else {		
			//if user inputs name, initial with that name
			serverName = args[0];
		}
				
		//Print title
		System.out.println("Deonte's Inet Client, 1.7.\n");
			
		//Print user input name or default name
		System.out.println("Using server: " + serverName + ", Port: " + port);
			
		//Open stream for accepting input to this console
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		try {

			do {
					
				//ask user for host name/IP address
				System.out.print("Enter a hostname or an IP address, (quit) to end: ");
				System.out.flush();
					
				//initial host name with user input
				hostName = in.readLine();
					
				if(!hostName.equalsIgnoreCase("quit")) 
					printServerResponse(hostName, serverName);
					
			} while(!hostName.equalsIgnoreCase("quit"));
				
			System.out.println("Cancelled by user request.");
				
		} catch(IOException x) { x.printStackTrace(); }
	}
			
	//allow access to class
	public static WebClient Instance() {
			
		if(instance == null) 
			instance = new WebClient();
		return instance;
			
	}

	private void printServerResponse(String request, String serverName) {
			
		try {
				
			/* Open connection to server port choose your own port # */
			sock = new Socket(serverName, port);
				
			//Create filter I/O streams for the socket:
			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			out = new PrintStream(sock.getOutputStream());
				
			//send request to server
			out.println(request);
			out.flush();
				
			//read in all content from server
			while((input = in.readLine()) != null) {
					
				//print data to console, if data was entered
				if(input != null) 
					System.out.println(input);	
			}
				
			//close connection
			sock.close();
			in.close();
			out.close();
				
		} catch(IOException x) {
			System.out.println("Socket error.");
			x.printStackTrace();
		}	
	}
		
	//class can not be instantiated
	private WebClient() {
		
		serverName = null;
		instance = null;
		hostName = null;
		sock = null;
		in = null;
		out = null;
		input = null;
		port = 4567;
			
	}
		
	private String serverName;
	private static WebClient instance;
	private int port;
	private String hostName;
	private Socket sock;
	private BufferedReader in;
	private PrintStream out;
	private String input;
		
}
