package djohnson.git.networking.webServer;

public interface Service {

	public void init();
	public void doGet(HttpRequest _request, HttpResponse _response);
	public void doPost(HttpRequest _request, HttpResponse _response);
	public void doPut(HttpRequest _request, HttpResponse _response);
	public void doDelete(HttpRequest _request, HttpResponse _response);
	public void doHead(HttpRequest _request, HttpResponse _response);
	
}
