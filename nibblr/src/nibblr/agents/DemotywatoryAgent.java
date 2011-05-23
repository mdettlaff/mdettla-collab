package nibblr.agents;

import nibblr.sources.Demotywatory;
import nibblr.sources.FeedSource;

public class DemotywatoryAgent extends WebsiteAgent {

	@Override
	protected FeedSource getFeedSource() {
		return new Demotywatory();
	}

}
