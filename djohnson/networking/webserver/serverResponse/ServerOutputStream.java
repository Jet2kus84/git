package djohnson.git.networking.webServer.serverResponse;

import java.io.DataOutputStream;
import java.io.IOException;

public class ServerOutputStream {

	/**
	 * 
	 * @param b the data to be written
	 * @throws IOException
	 */
	public void write(byte[] b) throws IOException {
		Ostream.write(b);
	}
	
	/**
	 * 
	 * @param b the data
	 * @param off the start off set in the data
	 * @param len the number of bytes to write
	 * @throws IOException
	 */
	public void write(byte[] b, int off, int len) throws IOException {
		Ostream.write(b, off, len);
	}
	
	/**
	 * 
	 * @throws IOException
	 */
	public void close() throws IOException {
		Ostream.close();
	}
	
	/**
	 * 
	 * @throws IOException
	 */
	public void flush() throws IOException {
		Ostream.flush();
	}
	
	ServerOutputStream(java.io.OutputStream _out) {
		Ostream = new DataOutputStream(_out);
	}
	
	private DataOutputStream Ostream;
}
