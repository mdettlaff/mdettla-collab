package nibblr.sources;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import nibblr.domain.Feed;
import nibblr.domain.FeedItem;
import nibblr.http.FakeHttpRequestFactory;

import org.junit.Test;

public class DeliciousTest {

	@Test
	public void parseDate() throws ParseException {
		String dateToParse = "2010-11-17T20:32:11Z";
		assertEquals(getExampleDate(), Delicious.parseDate(dateToParse));
	}

	@Test
	public void downloadOneFeedItem() throws ParseException {
		FeedSource delicious = new Delicious(new FakeHttpRequestFactory());
		Feed feedInfo = delicious.downloadFeedInfo();
		assertFeedInfo(feedInfo);
		Feed feed = delicious.downloadFeedWithItems();
		assertFeedInfo(feed);
		List<FeedItem> items = feed.getItems();
		assertFalse("No feed items found.", items.isEmpty());
		FeedItem actual = items.get(0);
		FeedItem expected = getExpectedItem();
		assertEquals(expected, actual);
		assertEquals(expected.getDate(), actual.getDate());
	}

	private void assertFeedInfo(Feed feed) {
		assertEquals("http://delicious.com/", feed.getUrl());
		assertEquals("Delicious", feed.getName());
		assertEquals("Bookmarking service", feed.getDescription());
	}

	private FeedItem getExpectedItem() throws ParseException {
		FeedItem expected = new FeedItem();
		expected.setUrl("http://java.sun.com/developer/technicalArticles/Programming/Stacktrace/");
		expected.setTitle("An Introduction to Java Stack Traces");
		expected.setHTMLContent("Useful advice on debugging Java programs.");
		expected.setDate(DateFormat.getDateTimeInstance().parse("2011-02-22 20:56:06"));
		return expected;
	}

	private static Date getExampleDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, 2010);
		calendar.set(Calendar.MONTH, 10);
		calendar.set(Calendar.DAY_OF_MONTH, 17);
		calendar.set(Calendar.HOUR_OF_DAY, 20);
		calendar.set(Calendar.MINUTE, 32);
		calendar.set(Calendar.SECOND, 11);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
}
