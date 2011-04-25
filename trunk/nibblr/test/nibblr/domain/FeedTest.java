package nibblr.domain;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

public class FeedTest {

	@Test
	public void gettersAndSetters() {
		Feed feed = getExampleFeed();

		assertEquals("delicious", feed.getName());
		assertEquals("http://delicious.com", feed.getUrl());
		assertEquals("Bookmarking service", feed.getDescription());
		List<FeedItem> items = feed.getItems();
		assertEquals(1, items.size());
		FeedItem firstItem = items.get(0);
		assertEquals("A cool page", firstItem.getTitle());
		assertEquals("http://coolpage.com", firstItem.getUrl());
		assertEquals("the content", firstItem.getHTMLContent());
		assertEquals(FeedItemTest.NOW, firstItem.getDate());
	}

	private Feed getExampleFeed() {
		Feed feed = new Feed();
		feed.setName("delicious");
		feed.setUrl("http://delicious.com");
		feed.setDescription("Bookmarking service");
		FeedItem item = FeedItemTest.getExampleFeedItem();
		feed.addItem(item);
		return feed;
	}
}
