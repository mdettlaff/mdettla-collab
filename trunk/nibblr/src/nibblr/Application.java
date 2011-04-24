package nibblr;

import nibblr.service.FeedService;

public interface Application {

	void setFeedService(FeedService feedService);

	void startup();
}
