package nibblr.ontology;

import jade.content.Concept;

import java.util.List;

import nibblr.domain.Feed;
import nibblr.domain.FeedItem;

public class UpdatingSubscription extends Feed implements Concept {

	public UpdatingSubscription() {
	}

	public UpdatingSubscription(Feed feed, List<FeedItem> items) {
		setName(feed.getName());
		setUrl(feed.getUrl());
		setItems(items);
	}

	public void setItems(List<FeedItem> items) {
		this.items = items;
	}
}
