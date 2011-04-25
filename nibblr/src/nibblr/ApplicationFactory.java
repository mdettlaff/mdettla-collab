package nibblr;

import nibblr.service.FeedService;

public class ApplicationFactory {

	private static Application instance = new Nibblr();

	public static Application getInstance(FeedService feedService) {
		instance.setFeedService(feedService);
		return instance;
	}

	/**
	 * For testing purposes only, do not use in production code.
	 */
	static void setInstance(Application application) {
		instance = application;
	}
}
