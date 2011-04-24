package nibblr.ontology;

import jade.content.Concept;
import nibblr.domain.Feed;

public class AddingSubscription extends Feed implements Concept {

	public AddingSubscription() {
	}

	public AddingSubscription(Feed feed) {
		setName(feed.getName());
		setUrl(feed.getUrl());
	}
}
