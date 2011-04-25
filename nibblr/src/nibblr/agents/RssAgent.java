package nibblr.agents;

import nibblr.sources.FeedSource;
import nibblr.sources.RssSource;

public class RssAgent extends WebsiteAgent {

	private String url;

	@Override
	public void setup() {
		super.setup();
		url = getArguments()[0].toString();
	}

	@Override
	protected FeedSource getFeedSource() {
		return new RssSource(url, requestFactory);
	}
}
