package nibblr.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Feed {

	private String name;
	private String url;
	private List<String> tags;
	private List<FeedItem> items;

	public Feed() {
		tags = new ArrayList<String>();
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

	public void addTags(String... tags) {
		this.tags.addAll(Arrays.asList(tags));
	}

	public List<String> getTags() {
		return new ArrayList<String>(tags);
	}

	public void addItem(FeedItem item) {
		items.add(item);
	}

	public List<FeedItem> getItems() {
		return items;
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
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Feed other = (Feed) obj;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}
}
