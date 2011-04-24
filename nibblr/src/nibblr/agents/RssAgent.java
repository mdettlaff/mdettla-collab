package nibblr.agents;

import java.util.List;

import nibblr.domain.Feed;
import nibblr.domain.FeedItem;
import nibblr.sources.FeedItemsSource;
import nibblr.sources.RssSource;

public class RssAgent extends WebsiteAgent {

	private String url;

	@Override
	public void setup() {
		super.setup();
		url = getArguments()[0].toString();
	}

	@Override
	Feed getFeedWithNoItems() {
		Feed feed = new Feed();
		feed.setName(getLocalName());
		feed.setUrl(url);
		return feed;
	}

	@Override
	public List<FeedItem> downloadItems() {
		FeedItemsSource source = new RssSource(url, requestFactory);
		return source.downloadItems();
	}
}
