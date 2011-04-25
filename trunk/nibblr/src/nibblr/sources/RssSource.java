package nibblr.sources;

import java.io.StringReader;

import nibblr.domain.Feed;
import nibblr.domain.FeedItem;
import nibblr.http.HttpRequest;
import nibblr.http.HttpRequestFactory;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;

public class RssSource implements FeedSource {

	private final HttpRequestFactory requestFactory;
	private final String url;

	public RssSource(String url, HttpRequestFactory requestFactory) {
		this.url = url;
		this.requestFactory = requestFactory;
	}

	@Override
	public Feed downloadFeedInfo() {
		SyndFeed rssFeed = downloadRssFeed();
		return convertFeedInfo(rssFeed);
	}

	@Override
	public Feed downloadFeedWithItems() {
		SyndFeed rssFeed = downloadRssFeed();
		Feed feed = convertFeedInfo(rssFeed);
		for (Object rssItem : rssFeed.getEntries()) {
			feed.addItem(convertFeedItem((SyndEntry)rssItem));
		}
		return feed;
	}

	private SyndFeed downloadRssFeed() {
		SyndFeed rssFeed;
		try {
			HttpRequest request = requestFactory.createRequest(url);
			String rss = request.doGet();
			SyndFeedInput input = new SyndFeedInput();
			rssFeed = input.build(new StringReader(rss));
		} catch (FeedException e) {
			throw new FeedDownloadingException(e, url);
		}
		return rssFeed;
	}

	private Feed convertFeedInfo(SyndFeed rssFeed) {
		Feed feed = new Feed();
		String link = rssFeed.getLink();
		String url = link != null ? link : rssFeed.getUri();
		feed.setUrl(url);
		feed.setName(rssFeed.getTitle());
		feed.setDescription(rssFeed.getDescription());
		return feed;
	}

	private FeedItem convertFeedItem(SyndEntry rssItem) {
		FeedItem item = new FeedItem();
		item.setTitle(rssItem.getTitle());
		item.setUrl(rssItem.getUri());
		item.setHTMLContent(rssItem.getDescription().getValue());
		item.setDate(rssItem.getPublishedDate());
		return item;
	}
}
