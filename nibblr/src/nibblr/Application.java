package nibblr;

import nibblr.domain.FeedService;

public interface Application {

	void setFeedService(FeedService feedService);

	void startup();
}
