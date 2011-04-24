package nibblr;

import static org.junit.Assert.assertEquals;
import jade.Boot;

import java.util.Set;

import nibblr.agents.PersonalAgent;
import nibblr.domain.Feed;
import nibblr.domain.FeedService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FeedServiceIntegrationTest implements Application {

	private Application defaultApp;
	private FeedService feedService;

	@Before
	public void setUp() throws InterruptedException {
		defaultApp = ApplicationFactory.getInstance();
		ApplicationFactory.setInstance(this);
		startAgentPlatform();
		Thread.sleep(PersonalAgent.SUBSCRIPTION_LIST_REFRESH_INTERVAL + 100);
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
	}

	@Override
	public void setFeedService(FeedService feedService) {
		this.feedService = feedService;
	}

	@Override
	public void startup() {
		// normally UI is initialized here, we don't need it in this test
	}

	@Test
	public void testGetAllFeeds() {
		Set<Feed> feeds = feedService.getAllFeeds();
		assertEquals(1, feeds.size());
		Feed feed = feeds.iterator().next();
		assertEquals("Delicious", feed.getName());
		assertEquals("http://delicious.com/", feed.getUrl());
	}
}
