package djohnson.git.networking.webServer.http;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.Socket;
import java.util.Date;
import java.util.StringTokenizer;

public class WebWorker implements Runnable {

	@Override
	public void run() {
		
		out.println("Waitng for http request: ");
		out.flush();
		
		try {
			
			//get first line off input
			String input = in.readLine();
			
			System.out.println("Client request: " + input);
			
			//if(input == null) return;
			
			//get a tokenizer to parse method
			parse = new StringTokenizer(input);
			
			//get command (GET, HEAD, POST, etc)
			String method = parse.nextToken();
	
			//get the requested file
			fileRequested = parse.nextToken();
			
			System.out.println("File requested: " + fileRequested);
			
			//give default file if no spec file is requested
			if(fileRequested.endsWith("/")) 
				fileRequested += DEFAULT_FILE;
			
			//get the file requested
			File  file = new File(this.ROOT_DIRECTORY, fileRequested.toLowerCase());
			
			//get the length of the file
			int fileLength = (int)file.length();
			
			//get file extension
			String content = getContentType(fileRequested);
			
			//if request is GET, send the file content
			if(method.toUpperCase().equals("GET")) {
					
				//create byte array for file data
				byte[] fileData = new byte[fileLength];
				
				//create file input with file requested
				FileInputStream fileIn = new FileInputStream(file);
				fileIn.read(fileData);
				
				//send HTTP headers
		        out.println("HTTP/1.1 200 OK");
		        out.println("Server: Java HTTP Server 1.1");
		        out.println("Date: " + new Date());
		        out.println("Content-type: " + content);
		        out.println("Content-length: " + file.length());
		        out.println(); //blank line between headers and content
		        out.flush(); //flush character output stream buffer

		        //send file contents to client
		        data.write(fileData,0,fileLength); 
		        data.flush(); //flush binary output stream buffer
		       
		        //open the file requested
		        java.awt.Desktop.getDesktop().open(new File(this.ROOT_DIRECTORY + fileRequested));
		        
		        System.out.println("File: " + fileRequested + 
		        		" was sent successfully to " + client.getInetAddress().getHostName());
		        
		        //close file that was opened
		        fileIn.close();
			}
			
		} catch (FileNotFoundException e) {
			this.fileNotFound(out, fileRequested);
		}
		catch (IOException e) {
			System.err.println("Server error: " + e);
		}
		catch (NullPointerException e) {
			return;
		}
		finally {
			close(out);
			close(in);
			close(client);
			close(data);
		}
	}
	
	private void fileNotFound(PrintWriter out, String file) {
		//send file not found HTTP headers
	    out.println("HTTP/1.1 404 File Not Found");
	    out.println("Server: Java HTTP Server 1.1");
	    out.println("Date: " + new Date());
	    out.println("Content-Type: text/html");
	    out.println();
	    out.println("<HTML>");
	    out.println("<HEAD><TITLE>File Not Found</TITLE>" +
	      "</HEAD>");
	    out.println("<BODY>");
	    out.println("<H2>404 File Not Found: " + file + "</H2>");
	    out.println("</BODY>");
	    out.println("</HTML>");
	    out.flush();
	}
	
	private String getContentType(String file) {
		
		if(file.endsWith(".htm") || file.endsWith(".html")) {
			return "text/html";
		}
		else if (file.endsWith(".gif")) {
			return "image/gif";
		}
		else if(file.endsWith(".jpg") || file.endsWith(".jpeg")) {
			return "image/jpeg";
		}
		else if(file.endsWith(".jar") || file.endsWith(".class")) {
			return "applicaton/octet-stream";
		}
		else {
			return "text/plain";
		}
	}
	
	private void close(Object stream)
	  {
	    if (stream == null)
	      return;

	    try
	    {
	      if (stream instanceof Reader)
	      {
	        ((Reader)stream).close();
	      }
	      else if (stream instanceof Writer)
	      {
	        ((Writer)stream).close();
	      }
	      else if (stream instanceof InputStream)
	      {
	        ((InputStream)stream).close();
	      }
	      else if (stream instanceof OutputStream)
	      {
	        ((OutputStream)stream).close();
	      }
	      else if (stream instanceof Socket)
	      {
	        ((Socket)stream).close();
	      }
	      else
	      {
	        System.err.println("Unable to close object: " + stream);
	      }
	    }
	    catch (Exception e)
	    {
	      System.err.println("Error closing stream: " + e);
	    }
	  }
	
	public WebWorker(Socket _client) {
		
		this.client = _client;
		fileRequested = null;
		
		try {
			
			in = new BufferedReader(new InputStreamReader(_client.getInputStream()));
			out = new PrintWriter(_client.getOutputStream());
			data = new BufferedOutputStream(_client.getOutputStream());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//ROOT = new File(".");
		DEFAULT_FILE = "index.html";
		ROOT_DIRECTORY = "/users/Jet2kus84/desktop/ClientAccess/";
	}
	
	//private final File ROOT;
	private final String ROOT_DIRECTORY;
	private final String DEFAULT_FILE;
	private BufferedReader in;
	private PrintWriter out;
	private Socket client;
	private BufferedOutputStream data;
	private StringTokenizer parse;
	private String fileRequested;
	
}
