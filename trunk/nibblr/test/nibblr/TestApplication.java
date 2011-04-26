package nibblr;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import junit.framework.AssertionFailedError;
import nibblr.domain.Feed;
import nibblr.service.FeedHandler;
import nibblr.service.FeedService;

public class TestApplication implements Application {

	private static final long WAIT_LIMIT = 5000;
	private static final long POLLING_INTERVAL = 50;

	private FeedService feedService;
	private Set<Feed> userFeeds;
	private boolean areUserFeedsUpdated;

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

	public Set<Feed> downloadAllFeedsAndSelectUserFeeds() {
		final Set<Feed> allFeeds = new LinkedHashSet<Feed>();
		feedService.downloadListOfAllFeeds(new FeedHandler() {
			@Override
			public void handleFeed(Feed foundFeed) {
				allFeeds.add(foundFeed);
				if (foundFeed.getUrl().contains("joemonster")) {
					userFeeds.add(foundFeed);
				}
			}
		});
		waitForCondition(new Predicate() {
			@Override public boolean eval() {
				return allFeeds.size() >= 2;
			}
		});
		return allFeeds;
	}

	public void updateUserFeeds() {
		feedService.updateFeeds(userFeeds, new FeedHandler() {
			@Override
			public void handleFeed(Feed updatedFeed) {
				if (userFeeds.contains(updatedFeed)) {
					userFeeds.remove(updatedFeed);
				}
				userFeeds.add(updatedFeed);
				areUserFeedsUpdated = true;
			}
		});
		waitForCondition(new Predicate() {
			@Override public boolean eval() {
				return areUserFeedsUpdated;
			}
		});
	}

	public Set<Feed> getUserFeeds() {
		return Collections.unmodifiableSet(userFeeds);
	}

	private static void waitForCondition(Predicate condition) {
		long startTime = System.currentTimeMillis();
		while (!condition.eval()) {
			if (System.currentTimeMillis() - startTime > WAIT_LIMIT)
				throw new AssertionFailedError(
						"Timeout after " + WAIT_LIMIT + " milliseconds.");
			try {
				Thread.sleep(POLLING_INTERVAL);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
