package nibblr.data;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import nibblr.domain.Feed;
import nibblr.domain.FeedItem;
import nibblr.gui.CompositeFilter.Filter;

public class DataSimple implements Data {
	
	private List<Feed> feeds;
	private Map<FeedItem, Feed> items;
	private List<FeedItem> read;
	
	public DataSimple() {
		feeds = new LinkedList<Feed>();
		items = new LinkedHashMap<FeedItem, Feed>();
		read = new LinkedList<FeedItem>();
	}

	@Override
	public void addFeed(Feed feed) {
		if(!feeds.contains(feed)) {
			feeds.add(feed);
			for(FeedItem item: feed.getItems())
				items.put(item, feed);
		}
	}

	@Override
	public Feed getFeed(FeedItem feedItem) throws DataNotFoundException {
		if(!items.containsKey(feedItem))
			throw new DataNotFoundException();
		return items.get(feedItem);
	}
	
	@Override
	public List<Feed> getFeeds() {
		Collections.sort(feeds, new ComparatorFeed());
		return feeds;
	}

	@Override
	public List<FeedItem> getFeedItems(Feed feed, Filter filter) throws DataNotFoundException {
		if(!feeds.contains(feed))
			throw new DataNotFoundException();
		feed = feeds.get(feeds.indexOf(feed));
		List<FeedItem> feedItems = new LinkedList<FeedItem>();
		switch(filter) {
		case READ:
			for(FeedItem feedItem: feed.getItems())
				if(isRead(feedItem))
					feedItems.add(feedItem);
			break;
		case UNRREAD:
			for(FeedItem feedItem: feed.getItems())
				if(!isRead(feedItem))
					feedItems.add(feedItem);
			break;
		default:
			feedItems = new LinkedList<FeedItem>(feed.getItems());
		}
		Collections.sort(feedItems, new ComparatorFeedItem());
		return feedItems;
	}

	@Override
	public void removeFeed(Feed feed) {
		feeds.remove(feed);
		for(FeedItem item: feed.getItems())
			items.remove(item);
	}
	
	@Override
	public void updateFeedItems(Feed feed) throws DataNotFoundException {
		if(!feeds.contains(feed))
			throw new DataNotFoundException();
		Feed f = feeds.get(feeds.indexOf(feed));
		for(FeedItem feedItem: feed.getItems()) {
			items.put(feedItem, f);
			feedItem.setDate(new Date());
		}
		f.setItems(feed.getItems());
	}
	
	public void read(FeedItem feedItem) {
		if(!read.contains(feedItem))
			read.add(feedItem);
	}
	
	public boolean isRead(FeedItem feedItem) {
		if(read.contains(feedItem))
			return true;
		return false;
	}
	
	class ComparatorFeed implements Comparator<Feed> {
		@Override
		public int compare(Feed feed1, Feed feed2) {
			return feed1.getName().compareTo(feed2.getName());
		}
	}
	
	class ComparatorFeedItem implements Comparator<FeedItem> {
		@Override
		public int compare(FeedItem feedItem1, FeedItem feedItem2) {
			return 0 - feedItem1.getDate().compareTo(feedItem2.getDate());
		}
	}
	
	public List<FeedItem> search(String keywords) {
		String[] k = keywords.trim().toLowerCase().split("\\s+");
		String join = join(k, "|");
		if(join == "")
			return new LinkedList<FeedItem>();
		String regex = ".*(" + join + ").*";
		List<FeedItem> find = new LinkedList<FeedItem>();
		for(FeedItem item: items.keySet())
			if(Pattern.matches(regex, item.getTitle().toLowerCase()))
				find.add(item);
		return find;
	}
	
	// private
	
	private String join(String[] s, String delimiter) {
	    if (s == null || s.length == 0)
	    	return "";
	    StringBuilder builder = new StringBuilder(s[0]);
	    for(int i = 1; i < s.length; i++) {
	      builder.append(delimiter).append(s[i]);
	    }
	    return builder.toString();
	}
}
