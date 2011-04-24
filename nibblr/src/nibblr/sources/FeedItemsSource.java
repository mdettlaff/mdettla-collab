package nibblr.sources;

import java.util.List;

import nibblr.domain.FeedItem;

public interface FeedItemsSource {

	List<FeedItem> downloadItems();
}
