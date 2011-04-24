package nibblr;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import jade.Boot;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
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
				"joemonster:nibblr.agents.RssAgent(http://www.joemonster.org/backend.php);" +
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
		assertEquals(2, feeds.size());
		Feed delicious = new Feed();
		delicious.setUrl("http://delicious.com/");
		Feed joeMonster = new Feed();
		joeMonster.setUrl("http://www.joemonster.org/backend.php");
		Set<Feed> expectedFeeds = new HashSet<Feed>(Arrays.asList(delicious, joeMonster));
		assertEquals(expectedFeeds, feeds);
		Iterator<Feed> feedIter = feeds.iterator();
		assertTrue(feedIter.next().getItems().isEmpty());
		assertTrue(feedIter.next().getItems().isEmpty());

		testApp.updateUserFeeds();
		Thread.sleep(250);
		assertEquals(1, testApp.getUserFeeds().size());
		Feed updatedFeed = testApp.getUserFeeds().iterator().next();
		assertEquals(joeMonster, updatedFeed);
		assertEquals("joemonster", updatedFeed.getName());
		assertEquals(2, updatedFeed.getItems().size());
		FeedItem actualItem = updatedFeed.getItems().iterator().next();
		FeedItem expectedItem = new FeedItem();
		expectedItem.setTitle("Autentyki CCCXCIII - Lekarskie pogaduchy");
		expectedItem.setUrl("http://www.joemonster.org/art/16474/" +
				"Autentyki_CCCXCIII_Lekarskie_pogaduchy");
		expectedItem.setHTMLContent("Dziś o nerwowym kierowcy, szóstoklasiście, który " +
				"sobie przemyśliwuje, oraz o najlepszym na świecie czujniku " +
				"cofania.");
		assertEquals(expectedItem, actualItem);
	}
}
