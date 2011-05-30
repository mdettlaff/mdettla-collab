package nibblr.http;

public interface HttpRequest {

	String doGet();

	String doGet(String headerName, String headerValue);
}
