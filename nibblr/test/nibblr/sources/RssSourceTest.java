package nibblr.sources;

import static org.junit.Assert.assertEquals;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.List;

import nibblr.domain.FeedItem;
import nibblr.domain.FeedItemsSource;
import nibblr.http.FakeHttpRequestFactory;
import nibblr.http.HttpRequestFactory;

import org.junit.Test;

public class RssSourceTest {

	@Test
	public void getItemsFromRss() throws ParseException {
		String url = "http://www.joemonster.org/backend.php";
		HttpRequestFactory requestFactory = new FakeHttpRequestFactory();
		FeedItemsSource source = new RssSource(url, requestFactory);
		List<FeedItem> items = source.downloadItems();
		assertEquals(2, items.size());
		DateFormat dateFormat = DateFormat.getDateTimeInstance();
		{
			FeedItem expectedItem = new FeedItem();
			expectedItem.setTitle("Autentyki CCCXCIII - Lekarskie pogaduchy");
			expectedItem.setUrl("http://www.joemonster.org/art/16474/" +
					"Autentyki_CCCXCIII_Lekarskie_pogaduchy");
			expectedItem.setHTMLContent("Dziś o nerwowym kierowcy, szóstoklasiście, który " +
					"sobie przemyśliwuje, oraz o najlepszym na świecie czujniku " +
					"cofania.");
			expectedItem.setDate(dateFormat.parse("2011-03-06 05:00:01"));
			FeedItem actualItem = items.get(0);
			assertFeedItemsExactlyEqual(expectedItem, actualItem);
		}
		{
			FeedItem expectedItem = new FeedItem();
			expectedItem.setTitle("Wielopak Weekendowy CDVI");
			expectedItem.setUrl("http://www.joemonster.org/art/16473/Wielopak_Weekendowy_CDVI");
			expectedItem.setHTMLContent("W dzisiejszym wielopaku zaprosimy kogoś do mieszkania, " +
					"porozmawiamy kulturalnie z policją i dowiemy się, po co komu potrzebna " +
					"<i>szynszyla</i>.");
			expectedItem.setDate(dateFormat.parse("2011-03-05 05:00:01"));
			FeedItem actualItem = items.get(1);
			assertFeedItemsExactlyEqual(expectedItem, actualItem);
		}
	}

	private void assertFeedItemsExactlyEqual(
			FeedItem expectedItem, FeedItem actualItem) {
		assertEquals(expectedItem, actualItem);
		assertEquals(expectedItem.getDate(), actualItem.getDate());
	}
}
