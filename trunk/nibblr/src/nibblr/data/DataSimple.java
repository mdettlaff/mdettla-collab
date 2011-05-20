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
	
	private final String FEED_ALL_NAME = "<All>";
	private final String FEED_ALL_DESCRIPTION = "Shows all the items in your subscribed channels";
	
	private List<Feed> feeds;
	private Map<FeedItem, Feed> items;
	private List<FeedItem> read;
	
	private Feed all;
	
	public DataSimple() {
		feeds = new LinkedList<Feed>();
		items = new LinkedHashMap<FeedItem, Feed>();
		read = new LinkedList<FeedItem>();
		all = new Feed();
		all.setName(FEED_ALL_NAME);
		all.setUrl("");
		all.setDescription(FEED_ALL_DESCRIPTION);
		all.setItems(new LinkedList<FeedItem>());
		feeds.add(all);
	}

	@Override
	public void addFeed(Feed feed) {
		if(!feeds.contains(feed)) {
			feeds.add(feed);
			for(FeedItem item: feed.getItems())
				items.put(item, feed);
			setFeedAll();
		}
	}
	
	@Override
	public List<Feed> getFeeds() {
		Collections.sort(feeds, new ComparatorFeed());
		return feeds;
	}
	
	@Override
	public List<Feed> getFeedsToSynchronize() {
		List<Feed> feeds = new LinkedList<Feed>(this.feeds);
		feeds.remove(all);
		return feeds;
	}
	
	@Override
	public boolean isFeedSynchronizable(Feed feed) {
		if(!feeds.contains(feed) || feed == all)
			return false;
		return true;
	}

	@Override
	public List<FeedItem> getItems(Feed feed, Filter filter) throws DataNotFoundException {
		if(!feeds.contains(feed))
			throw new DataNotFoundException();
		feed = feeds.get(feeds.indexOf(feed));
		List<FeedItem> items = new LinkedList<FeedItem>();
		switch(filter) {
		case READ:
			for(FeedItem item: feed.getItems())
				if(isRead(item))
					items.add(item);
			break;
		case UNRREAD:
			for(FeedItem item: feed.getItems())
				if(!isRead(item))
					items.add(item);
			break;
		default:
			items = new LinkedList<FeedItem>(feed.getItems());
		}
		Collections.sort(items, new ComparatorFeedItem());
		return items;
	}

	@Override
	public void remove(Feed feed) {
		if(feed == all)
			return;
		feeds.remove(feed);
		for(FeedItem item: feed.getItems())
			items.remove(item);
		setFeedAll();
	}
	
	@Override
	public void update(Feed feed) throws DataNotFoundException {
		if(!feeds.contains(feed))
			throw new DataNotFoundException();
		Feed f = feeds.get(feeds.indexOf(feed));
		for(FeedItem item: feed.getItems())
			if(!items.containsKey(item)) {
				items.put(item, f);
				item.setDate(new Date());
			}
		f.setItems(feed.getItems());
		setFeedAll();
	}
	
	public boolean isFeedAll(Feed feed) {
		return feed == all;
	}
	
	public void read(FeedItem feedItem) {
		if(!read.contains(feedItem))
			read.add(feedItem);
	}
	
	public void readAll(Feed feed) throws DataNotFoundException {
		if(!feeds.contains(feed))
			throw new DataNotFoundException();
		feed = feeds.get(feeds.indexOf(feed));
		for(FeedItem item: feed.getItems())
			read(item);
	}
	
	public boolean isRead(FeedItem feedItem) {
		if(read.contains(feedItem))
			return true;
		return false;
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
		if(s == null || s.length == 0)
	    	return "";
		StringBuilder builder = new StringBuilder(s[0]);
	    for(int i = 1; i < s.length; i++)
	    	builder.append(delimiter).append(s[i]);
	    return builder.toString();
	}
	
	private void setFeedAll() {
		all.setItems(new LinkedList<FeedItem>(items.keySet()));
	}
	
	private class ComparatorFeed implements Comparator<Feed> {
		@Override
		public int compare(Feed feed1, Feed feed2) {
			return feed1.getName().compareTo(feed2.getName());
		}
	}
	
	private class ComparatorFeedItem implements Comparator<FeedItem> {
		@Override
		public int compare(FeedItem feedItem1, FeedItem feedItem2) {
			return 0 - feedItem1.getDate().compareTo(feedItem2.getDate());
		}
	}
}
