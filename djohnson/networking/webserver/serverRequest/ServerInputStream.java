package djohnson.git.networking.webServer.serverRequest;

public class ServerInputStream {

	/**
	 * 
	 * @return the next four bytes of this input stream interpreted as an int
	 * @throws java.io.IOException
	 */
	public int readInt() throws java.io.IOException {
		return Istream.readInt();
	}
	
	/**
	 * 
	 * @return the next four bytes of this input stream interpreted as an Double
	 * @throws java.io.IOException
	 */
	public Double readDouble() throws java.io.IOException {
		return Istream.readDouble();
	}
	
	/**
	 * 
	 * @return the next byte of data
	 * @throws java.io.IOException
	 */
	public int read() throws java.io.IOException {
		return Istream.read();
	}
	
	/**
	 * 
	 * @param b buffer into which the data is read
	 * @param off starting offset
	 * @param len max number of bytes
	 * @return total number of bytes read
	 * @throws java.io.IOException
	 */
	public int read(byte[] b, int off, int len) throws java.io.IOException {
		return Istream.read(b, off, len);
	}
	
	/**
	 * 
	 * @throws java.io.IOException
	 */
	public void close() throws java.io.IOException {
		Istream.close();
	}
	
	ServerInputStream(java.io.InputStream in) {
		Istream = new java.io.DataInputStream(in);
	}
	
	private java.io.DataInputStream Istream;
}
