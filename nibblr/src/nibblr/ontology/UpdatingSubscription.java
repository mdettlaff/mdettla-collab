package nibblr.ontology;

import jade.content.Concept;
import nibblr.domain.Feed;

public class UpdatingSubscription extends Feed implements Concept {

	public UpdatingSubscription() {
	}

	public UpdatingSubscription(Feed feed) {
		copyMetadataFrom(feed);
		setItems(feed.getItems());
	}
}
