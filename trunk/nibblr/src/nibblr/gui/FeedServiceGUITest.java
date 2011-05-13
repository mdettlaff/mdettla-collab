package nibblr.gui;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import nibblr.domain.Feed;
import nibblr.domain.FeedItem;
import nibblr.service.FeedHandler;
import nibblr.service.FeedService;

public class FeedServiceGUITest implements FeedService {
	
	private Random random;
	
	private List<Feed> feeds;
	
	private Timer timer;
	
	private FeedHandler foundHandler;
	
	public FeedServiceGUITest() {
		random = new Random();
		feeds = new LinkedList<Feed>();
		timer = new Timer();
		for(int i = 0; i < random.nextInt(20) + 10; i++) {
			feeds.add(createFeed());
		}
	}

	@Override
	public void downloadListOfAllFeeds(FeedHandler feedFoundHandler) {
		foundHandler = feedFoundHandler;
		for(Feed feed: feeds) {
			final Feed f = feed;
			timer.schedule(
				new TimerTask() {
					@Override
					public void run() {
						foundHandler.handleFeed(f);
					}
				},
				random.nextInt(2000));
		}
	}

	@Override
	public void updateFeeds(Collection<Feed> feedsToUpdate, FeedHandler feedUpdateHandler) {
		final FeedHandler handler = feedUpdateHandler;
		for(Feed feed: feedsToUpdate) {
			final Feed f = feed;
			timer.schedule(
				new TimerTask() {
					@Override
					public void run() {
						List<FeedItem> items = new LinkedList<FeedItem>(f.getItems());
						for(int i = 0; i < random.nextInt(5); i++)
							items.add(createFeedItem(f));
						f.setItems(items);
						handler.handleFeed(f);
					}
				},
				random.nextInt(2000));
		}
	}

	@Override
	public List<Feed> recommendFeeds() {
		return feeds;
	}
	
	// private
	
	private Feed createFeed() {
		Feed feed = new Feed();
		long t = random.nextLong();
		feed.setName("Channel " + t);
		feed.setUrl("http://www.channel" + t + ".org");
		feed.setDescription("Description channel " + t);
		return feed;
	}
	
	private FeedItem createFeedItem(Feed feed) {
		FeedItem item = new FeedItem();
		long t = random.nextLong();
		item.setTitle("Item " + t);
		item.setUrl(feed.getUrl() + "/item" + t);
		item.setHTMLContent("<h2>Item " + t + "</h2><p>Item content</p>");
		item.setDate(new Date(t - (long)(random.nextGaussian() * 2592000000l)));
		return item;
	}
}
