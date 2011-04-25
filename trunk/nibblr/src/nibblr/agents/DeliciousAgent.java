package nibblr.agents;

import nibblr.sources.Delicious;
import nibblr.sources.FeedSource;

public class DeliciousAgent extends WebsiteAgent {

	@Override
	protected FeedSource getFeedSource() {
		return new Delicious(requestFactory);
	}
}
