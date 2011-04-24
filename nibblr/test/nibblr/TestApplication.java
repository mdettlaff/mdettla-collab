package nibblr;

import java.util.LinkedHashSet;
import java.util.Set;

import nibblr.domain.Feed;
import nibblr.service.FeedService;
import nibblr.service.FeedUpdateHandler;

public class TestApplication implements Application {

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

	public Set<Feed> downloadAllFeeds() {
		Set<Feed> allFeeds = feedService.getAllFeeds();
		for (Feed feed : allFeeds) {
			if (feed.getUrl().contains("joemonster")) {
				userFeeds.add(feed);
			}
		}
		return allFeeds;
	}

	public void updateUserFeeds() {
		feedService.updateFeeds(userFeeds, new FeedUpdateHandler() {
			@Override
			public void updateFeed(Feed updatedFeed) {
				if (userFeeds.contains(updatedFeed)) {
					userFeeds.remove(updatedFeed);
				}
				userFeeds.add(updatedFeed);
			}
		});
	}

	public Set<Feed> getUserFeeds() {
		return userFeeds;
	}
}
