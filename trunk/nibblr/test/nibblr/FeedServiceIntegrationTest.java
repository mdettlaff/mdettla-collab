package nibblr;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import jade.Boot;

import java.util.Set;

import nibblr.agents.PersonalAgent;
import nibblr.domain.Feed;
import nibblr.domain.FeedItem;
import nibblr.http.FakeHttpRequestFactory;
import nibblr.http.HttpRequestFactory;
import nibblr.http.HttpRequestFactoryFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FeedServiceIntegrationTest {

	private TestApplication testApp;
	private Application defaultApp;
	private HttpRequestFactory defaultRequestFactory;

	@Before
	public void setUp() throws InterruptedException {
		testApp = new TestApplication();
		initializeFactories(testApp);
		startAgentPlatform();
		Thread.sleep(PersonalAgent.SUBSCRIPTION_LIST_REFRESH_INTERVAL + 100);
	}

	private void initializeFactories(Application application) {
		defaultApp = ApplicationFactory.getInstance();
		ApplicationFactory.setInstance(application);
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

	@Test
	public void test() throws InterruptedException {
		Set<Feed> feeds = testApp.downloadAllFeeds();
		assertEquals(1, feeds.size());
		Feed feed = feeds.iterator().next();
		assertEquals("Delicious", feed.getName());
		assertEquals("http://delicious.com/", feed.getUrl());

		assertTrue(feed.getItems().isEmpty());
		testApp.updateUserFeeds();
		Thread.sleep(250);
		assertEquals(1, testApp.getUserFeeds().size());
		Feed updatedFeed = testApp.getUserFeeds().iterator().next();
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
