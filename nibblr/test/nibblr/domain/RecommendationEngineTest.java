package nibblr.domain;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class RecommendationEngineTest {

	@Test
	public void testRecommendFeeds() {
		Feed feed = new Feed();
		feed.setUrl("http://foo.com/");
		feed.setName("foo bar");
		Feed dissimilar = new Feed();
		dissimilar.setUrl("http://baz.com/");
		dissimilar.setName("baz qux");
		Feed similar = new Feed();
		similar.setUrl("http://bar.com/");
		similar.setName("bar");

		RecommendationEngine engine = new RecommendationEngine();
		engine.addFeed(feed);
		engine.addFeed(dissimilar);
		engine.addFeed(similar);
		engine.showInterestIn(feed);
		List<Feed> recommendations = engine.recommendFeeds();
		List<Feed> expected = Arrays.asList(similar, dissimilar);
		assertEquals(expected, recommendations);
	}
}
