package nibblr;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import jade.Boot;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import nibblr.domain.Feed;
import nibblr.domain.FeedItem;
import nibblr.http.FakeHttpRequestFactory;
import nibblr.http.HttpRequestFactory;
import nibblr.http.HttpRequestFactoryFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FeedServiceIntegrationTest {

	private static final int AGENT_PLATFORM_BOOT_WAIT_TIME = 1500;

	private TestApplication testApp;
	private Application defaultApp;
	private HttpRequestFactory defaultRequestFactory;

	@Before
	public void setUp() throws InterruptedException {
		testApp = new TestApplication();
		initializeFactories(testApp);
		startAgentPlatform();
		Thread.sleep(AGENT_PLATFORM_BOOT_WAIT_TIME);
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
		Set<Feed> allFeeds = testApp.downloadAllFeedsAndSelectUserFeeds();
		assertEquals(2, allFeeds.size());
		Feed delicious = new Feed();
		delicious.setUrl("http://delicious.com/");
		Feed joeMonster = new Feed();
		joeMonster.setUrl("http://www.joemonster.org/backend.php");
		Set<Feed> expectedFeeds = new HashSet<Feed>(Arrays.asList(delicious, joeMonster));
		assertEquals(expectedFeeds, allFeeds);
		Iterator<Feed> feedIter = allFeeds.iterator();
		assertTrue(feedIter.next().getItems().isEmpty());
		assertTrue(feedIter.next().getItems().isEmpty());
		assertEquals(1, testApp.getUserFeeds().size());

		testApp.updateUserFeeds();
		assertEquals(1, testApp.getUserFeeds().size());
		Feed updatedFeed = testApp.getUserFeeds().iterator().next();
		assertEquals(joeMonster, updatedFeed);
		assertEquals("joemonster", updatedFeed.getName());
		assertEquals(2, updatedFeed.getItems().size());
		FeedItem actualItem = updatedFeed.getItems().iterator().next();
		FeedItem expectedItem = getExpectedItem();
		assertEquals(expectedItem, actualItem);
	}

	private FeedItem getExpectedItem() {
		FeedItem expectedItem = new FeedItem();
		expectedItem.setTitle("Autentyki CCCXCIII - Lekarskie pogaduchy");
		expectedItem.setUrl("http://www.joemonster.org/art/16474/" +
				"Autentyki_CCCXCIII_Lekarskie_pogaduchy");
		expectedItem.setHTMLContent("Dziś o nerwowym kierowcy, szóstoklasiście, który " +
				"sobie przemyśliwuje, oraz o najlepszym na świecie czujniku " +
				"cofania.");
		return expectedItem;
	}
}
