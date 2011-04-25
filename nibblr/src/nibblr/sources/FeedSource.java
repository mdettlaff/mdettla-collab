package nibblr.sources;

import nibblr.domain.Feed;

public interface FeedSource {

	Feed downloadFeedInfo();

	Feed downloadFeedWithItems();
}
