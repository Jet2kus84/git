package djohnson.git.networking.webServer.serverRequest;

import java.io.BufferedReader;
import java.net.Socket;

/**
 * 
 * @author Jet2kus84
 *
	This is used to obtain request information
	and set request information from the user
 */

public abstract class ServerRequest {

	/**
	 * 
	 * @param _protocol http protocol name & version
	 */
	protected void setProtocol(String _protocol) {
		this.protocol = _protocol;
	}
	
	/**
	 * 
	 * @param _in input stream being used
	 */
	protected void setReader(java.io.InputStream _in) {
		in = new BufferedReader(new java.io.InputStreamReader(_in));
	}
	
	/**
	 * 
	 * @param in input stream being used
	 */
	protected void setInputStream(java.io.InputStream in) {
		dataIn = new ServerInputStream(in);
	}
	
	/**
	 * set the method of request(GET, PUT, etc)
	 * @param _method action type
	 */
	protected void setMethod(String _method) {
		method.setMethod(_method);
	}
		
	/**
	 * set the client request URL
	 * @param _url full request string
	 */
	protected void setRequestURL(String _url) {
		this.url.setURL(_url);
	}
	
	/**
	 * 
	 * @param _uri requested resource
	 */
	protected void setRequestURI(String _uri) {
		this.uri.setURI(_uri);
	}
	
	/**
	 * 
	 * @param content Mime type
	 */
	protected void setContentType(String content) {
		this.contentType = content;
	}
	
	/**
	 * 
	 * @return the port number to which the request was sent
	 */
	public int getServerPort() {
		return client.getLocalPort();
	}
	
	/**
	 * 
	 * @return host name of the server to which the request was sent
	 */
	public String getServerName() {
		return client.getInetAddress().getHostName();
	}
	
	/**
	 * 
	 * @return http protocol name & version
	 */
	public String getProtocol() {
		return protocol;
	}
	
	/**
	 * 
	 * @return reader for string input
	 */
	public BufferedReader getReader() {
		return in;
	}
	
	/**
	 * 
	 * @return input stream for data input
	 */
	public ServerInputStream getInputStream() {
		return dataIn;
	}
	
	/**
	 * 
	 * @return requested resource
	 */
	public String getRequestURI() {
		return uri.getURI().toLowerCase();
	}
	
	/**
	 * 
	 * @return method type
	 */
	public String getMethod() {
		return method.getMethod().toUpperCase();
	}
	
	/**
	 * 
	 * @return url used to make the request
	 */
	public String getRequestURL() {
		return url.getURL();
	}
	
	/**
	 * 
	 * @return the Internet Protocol (IP) address of the client or last proxy that sent the request
	 */
	public String getRemoteAddr() {
		return client.getInetAddress().getHostAddress();
	}
		
	public String getContentType() {
		return contentType;
	}
	
	protected ServerRequest(Socket _client) {
	
		if(_client == null) 
			throw new NullPointerException("Socket must be initialize first");
		else 
			this.client = _client;
	
		method = new HttpMethod();
		url = new URL();
		uri = new URI();
	}

	protected Socket client;
	private HttpMethod method;
	private ServerInputStream dataIn;
	private BufferedReader in;
	private URL url;
	private URI uri;
	private String protocol;
	private String contentType;
}
