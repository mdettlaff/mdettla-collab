package nibblr.agents;

import java.util.List;

import nibblr.domain.Feed;
import nibblr.domain.FeedItem;
import nibblr.sources.Delicious;

public class DeliciousAgent extends WebsiteAgent {

	@Override
	Feed getFeedWithNoItems() {
		Feed feed = new Feed();
		feed.setName("Delicious");
		feed.setUrl("http://delicious.com/");
		return feed;
	}

	@Override
	public List<FeedItem> downloadItems() {
		Delicious delicious = new Delicious(requestFactory);
		return delicious.downloadItems();
	}
}
