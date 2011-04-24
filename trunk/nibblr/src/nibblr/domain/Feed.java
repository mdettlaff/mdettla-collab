package nibblr.domain;

import java.util.ArrayList;
import java.util.List;

public class Feed {

	private String name;
	private String url;
	protected List<FeedItem> items;

	public Feed() {
		items = new ArrayList<FeedItem>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void addItem(FeedItem item) {
		items.add(item);
	}

	public List<FeedItem> getItems() {
		return items;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "[" +
		"url=" + url + ", items.size()=" + items.size() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Feed)) {
			return false;
		}
		Feed other = (Feed)obj;
		return getUrl().equals(other.getUrl());
	}
}
