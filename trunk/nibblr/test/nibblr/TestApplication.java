package nibblr;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import nibblr.domain.Feed;
import nibblr.service.FeedHandler;
import nibblr.service.FeedService;

public class TestApplication implements Application {

	private static final int ASYNCHRONOUS_CALL_WAIT_TIME = 250;

	private FeedService feedService;
	private Set<Feed> userFeeds;

	public TestApplication() {
		userFeeds = new LinkedHashSet<Feed>();
	}

	@Override
	public void setFeedService(FeedService feedService) {
		this.feedService = feedService;
	}

	@Override
	public void startup() {
		// normally UI is initialized here, we don't use it in tests
	}

	public Set<Feed> downloadAllFeedsAndSelectUserFeeds()
	throws InterruptedException {
		final Set<Feed> allFeeds = new LinkedHashSet<Feed>();
		feedService.downloadAllFeeds(new FeedHandler() {
			@Override
			public void handleFeed(Feed foundFeed) {
				allFeeds.add(foundFeed);
				if (foundFeed.getUrl().contains("joemonster")) {
					userFeeds.add(foundFeed);
				}
			}
		});
		waitForAsyncResponse();
		return allFeeds;
	}

	public void updateUserFeeds() throws InterruptedException {
		feedService.updateFeeds(userFeeds, new FeedHandler() {
			@Override
			public void handleFeed(Feed updatedFeed) {
				if (userFeeds.contains(updatedFeed)) {
					userFeeds.remove(updatedFeed);
				}
				userFeeds.add(updatedFeed);
			}
		});
		waitForAsyncResponse();
	}

	public Set<Feed> getUserFeeds() {
		return Collections.unmodifiableSet(userFeeds);
	}

	private void waitForAsyncResponse() throws InterruptedException {
		Thread.sleep(ASYNCHRONOUS_CALL_WAIT_TIME);
	}
}
