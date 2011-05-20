package nibblr.data;

import java.util.List;

import nibblr.domain.Feed;
import nibblr.domain.FeedItem;
import nibblr.gui.CompositeFilter.Filter;

public interface Data {
	public void addFeed(Feed feed);
	
	public List<Feed> getFeeds();
	
	public List<Feed> getFeedsToSynchronize();
	
	public boolean isFeedSynchronizable(Feed feed);
	
	public boolean isFeedAll(Feed feed);
	
	public List<FeedItem> getItems(Feed feed, Filter filter) throws DataNotFoundException;
	
	public void remove(Feed feed);
	
	public void update(Feed feed) throws DataNotFoundException;
	
	public void read(FeedItem feedItem);
	
	public void readAll(Feed feed) throws DataNotFoundException;
	
	public boolean isRead(FeedItem feedItem);
	
	public List<FeedItem> search(String keywords);
}
