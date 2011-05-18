package nibblr.data;

import java.util.List;

import nibblr.domain.Feed;
import nibblr.domain.FeedItem;
import nibblr.gui.CompositeFilter.Filter;

public interface Data {
	public void addFeed(Feed feed);

	public Feed getFeed(FeedItem feedItem) throws DataNotFoundException;
	
	public List<Feed> getFeeds();
	
	public List<FeedItem> getFeedItems(Feed feed, Filter filter) throws DataNotFoundException;
	
	public void removeFeed(Feed feed);
	
	public void updateFeedItems(Feed feed) throws DataNotFoundException;
	
	public void read(FeedItem feedItem);
	
	public boolean isRead(FeedItem feedItem);
	
	public List<FeedItem> search(String keywords);
}
