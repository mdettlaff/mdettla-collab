package nibblr;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import jade.Boot;

import java.util.LinkedHashSet;
import java.util.Set;

import nibblr.agents.PersonalAgent;
import nibblr.domain.Feed;
import nibblr.domain.FeedItem;
import nibblr.http.FakeHttpRequestFactory;
import nibblr.http.HttpRequestFactory;
import nibblr.http.HttpRequestFactoryFactory;
import nibblr.service.FeedService;
import nibblr.service.FeedUpdateHandler;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FeedServiceIntegrationTest implements Application {

	private Application defaultApp;
	private HttpRequestFactory defaultRequestFactory;
	private FeedService feedService;
	private Set<Feed> userFeeds;

	@Before
	public void setUp() throws InterruptedException {
		userFeeds = new LinkedHashSet<Feed>();
		initializeFactories();
		startAgentPlatform();
		Thread.sleep(PersonalAgent.SUBSCRIPTION_LIST_REFRESH_INTERVAL + 100);
	}

	private void initializeFactories() {
		defaultApp = ApplicationFactory.getInstance();
		ApplicationFactory.setInstance(this);
		defaultRequestFactory = HttpRequestFactoryFactory.getInstance();
		HttpRequestFactoryFactory.setInstance(new FakeHttpRequestFactory());
	}

	private void startAgentPlatform() {
		Boot.main(new String[] {
				"-local-host", "localhost", "-agents",
				"delicious:nibblr.agents.DeliciousAgent();" +
				"personal:nibblr.agents.PersonalAgent()"
		});
	}

	@After
	public void tearDown() {
		ApplicationFactory.setInstance(defaultApp);
		HttpRequestFactoryFactory.setInstance(defaultRequestFactory);
	}

	@Override
	public void setFeedService(FeedService feedService) {
		this.feedService = feedService;
	}

	@Override
	public void startup() {
		// normally UI is initialized here, we don't need it in this test
		FeedUpdateHandler feedUpdateHandler = new FeedUpdateHandler() {
			@Override
			public void updateFeed(Feed updatedFeed) {
				if (userFeeds.contains(updatedFeed)) {
					userFeeds.remove(updatedFeed);
				}
				userFeeds.add(updatedFeed);
			}
		};
		feedService.setFeedUpdateEventHandler(feedUpdateHandler);
	}

	@Test
	public void test() throws InterruptedException {
		Set<Feed> feeds = feedService.getAllFeeds();
		assertEquals(1, feeds.size());
		Feed feed = feeds.iterator().next();
		assertEquals("Delicious", feed.getName());
		assertEquals("http://delicious.com/", feed.getUrl());

		assertTrue(feed.getItems().isEmpty());
		userFeeds.add(feed);
		feedService.updateFeeds(userFeeds);
		Thread.sleep(250);
		assertEquals(1, userFeeds.size());
		Feed updatedFeed = userFeeds.iterator().next();
		assertEquals(feed, updatedFeed);
		assertEquals("Delicious", updatedFeed.getName());
		assertEquals(1, updatedFeed.getItems().size());
		FeedItem actual = updatedFeed.getItems().iterator().next();
		FeedItem expected = new FeedItem();
		expected.setUrl("http://java.sun.com/developer/technicalArticles/Programming/Stacktrace/");
		expected.setTitle("An Introduction to Java Stack Traces");
		expected.setHTMLContent("Useful advice on debugging Java programs.");
		assertEquals(expected, actual);
	}
}
