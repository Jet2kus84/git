package djohnson.git.networking.webServer.serverResponse;

import java.io.PrintWriter;
import java.net.Socket;

public abstract class ServerResponse {

	/**
	 * 
	 * @return the PrintWriter being used
	 */
	public PrintWriter getWriter() {
		if(out == null) {
			throw new NullPointerException("Must initialize this object before use.");
		}
		return out;
	}
	
	/**
	 * 
	 * @return the mime type 
	 */
	public String getContentType() {
		return mime.getType();
	}
	
	/**
	 * 
	 * @return the Data stream being used
	 */
	public ServerOutputStream getOutputStream() {
		if(dataOut == null)
			throw new NullPointerException("Must initialize this object before use.");
		else 
			return dataOut;
	}
	
	/**
	 * 
	 * @param writer OutputStream needed to initialize PrintWriter for output
	 */
	public void setWriter(java.io.OutputStream writer) {
		out = new PrintWriter(writer);
	}
	
	/**
	 * 
	 * @param out OutputStream needed to initialize ServerOuputStream for data output
	 */
	public void setServerOutputStream(java.io.OutputStream out) {
		dataOut = new ServerOutputStream(out);
	}

	/**
	 * 
	 * @param _type mime type
	 */
	public void setContentType(String _type) {
		mime.setMime(_type);
	}
	
	public ServerResponse(Socket _client) {
		this.client = _client;
		mime = new Mime();
	}
	
	private PrintWriter out;
	private ServerOutputStream dataOut;
	private Mime mime;
	protected Socket client;
}
