package djohnson.git.networking.webServer.serverResponse;

public class Mime {

	public String getType() {
		return type;
	}
	
	public void setMime(String _type) {
		this.type = _type;
	}
	
	public Mime() {
		type = null;
	}
	
	private String type;
}
