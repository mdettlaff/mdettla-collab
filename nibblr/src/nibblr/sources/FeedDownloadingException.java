package nibblr.sources;

public class FeedDownloadingException extends RuntimeException {

	public FeedDownloadingException(Exception cause, String url) {
		super("Error while downloading " + url, cause);
	}
}
