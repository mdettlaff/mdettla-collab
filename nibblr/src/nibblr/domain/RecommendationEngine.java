package nibblr.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class RecommendationEngine {

	private Map<Feed, Integer> levelsOfInterest;

	public RecommendationEngine() {
		levelsOfInterest = new LinkedHashMap<Feed, Integer>();
	}

	public void addFeed(Feed feed) {
		levelsOfInterest.put(feed, 0);
	}

	public void showInterestIn(Feed interestingFeed) {
		if (!levelsOfInterest.containsKey(interestingFeed)) {
			throw new IllegalArgumentException("Unknown feed: " + interestingFeed);
		}
		int levelOfInterest = levelsOfInterest.get(interestingFeed);
		levelsOfInterest.put(interestingFeed, levelOfInterest + 1);
	}

	/**
	 * Returns list of feeds unknown to the user, sorted from most
	 * recommended to least recommended.
	 */
	public List<Feed> recommendFeeds() {
		List<Feed> feedsToRecommend = getFeedsUnknownToUser();
		Collections.sort(feedsToRecommend, new Comparator<Feed>() {
			@Override
			public int compare(Feed feed1, Feed feed2) {
				return -rateFeed(feed1).compareTo(rateFeed(feed2));
			}
		});
		return feedsToRecommend;
	}

	private Double rateFeed(Feed feedToRate) {
		// TODO use a more sophisticated algorithm
		Feed mostInterestingFeed = levelsOfInterest.keySet().iterator().next();
		for (Map.Entry<Feed, Integer> entry : levelsOfInterest.entrySet()) {
			Feed feed = entry.getKey();
			int levelOfInterest = entry.getValue();
			if (levelOfInterest > levelsOfInterest.get(feedToRate)) {
				mostInterestingFeed = feed;
			}
		}
		double rating = 0;
		String[] words = feedToRate.getName().split("\\s");
		List<String> interestingWords =
			Arrays.asList(mostInterestingFeed.getName().split("\\s"));
		for (String word : words) {
			if (interestingWords.contains(word)) {
				rating++;
			}
		}
		return rating;
	}

	private List<Feed> getFeedsUnknownToUser() {
		List<Feed> feedsUnknownToUser = new ArrayList<Feed>();
		for (Map.Entry<Feed, Integer> entry : levelsOfInterest.entrySet()) {
			Feed feed = entry.getKey();
			int levelOfInterest = entry.getValue();
			if (levelOfInterest == 0) {
				feedsUnknownToUser.add(feed);
			}
		}
		return feedsUnknownToUser;
	}
}
