package nibblr.sources;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;
import nibblr.domain.Feed;
import nibblr.domain.FeedItem;

public class Demotywatory implements FeedSource {
	
	private static final String URL = "http://demotywatory.pl/";
	
	private static final String PATTERN_FIELD_DAY = "(\\d{1,2})";
	private static final String PATTERN_FIELD_MONTH = "([a-zA-Zęóąśłżźćń]+)";
	private static final String PATTERN_FIELD_YEAR = "(\\d{4})";
	private static final String PATTERN_FIELD_TIME = "(\\d{1,2}:\\d{1,2})";
	
	private static final String PATTERN_DATE =
		PATTERN_FIELD_DAY + "(\\s+)" +
		PATTERN_FIELD_MONTH + "(\\s+)" +
		PATTERN_FIELD_YEAR + "(.+)" +
		PATTERN_FIELD_TIME;

	@Override
	public Feed downloadFeedInfo() {
		Feed feed = new Feed();
		feed.setName("Demotywatory.pl");
		feed.setDescription("Demotywatory.pl - ironicznie i prawdziwie o rzeczach niekoniecznie ważnych.");
		feed.setUrl(URL);
		return feed;
	}

	@Override
	public Feed downloadFeedWithItems() {
		Feed feed = downloadFeedInfo();
		feed.setItems(downloadFeedItems());
		return feed;
	}
	
	// private
	
	private List<FeedItem> downloadFeedItems() {
		List<FeedItem> items;
		try {
			items = new LinkedList<FeedItem>();
			for(int page = 1; page <= 3; page++) {
				Source source = new Source(new URL(URL + "page/" + page));
				source.setLogger(null);
				for(Element element: source.getAllElements("id", Pattern.compile("^demot[0-9]+$"))) {
					FeedItem item = new FeedItem();
					
					Element title = element.getFirstElement(HTMLElementName.H1);
					item.setTitle(title.getTextExtractor().toString());
					
					Element date = element.getFirstElementByClass("infobar");
					item.setDate(parseDate(date.getTextExtractor().toString(), "pl"));
			
					Element body = element.getFirstElementByClass("demot_pic");
					Element url = body.getFirstElement(HTMLElementName.A);
					item.setUrl(url.getAttributeValue("href"));
					
					Element img = url.getFirstElement(HTMLElementName.IMG);
					String imgSrc = img.getAttributeValue("src");
					String imgAlt = img.getAttributeValue("alt");
					item.setHTMLContent("<img src=\"" + imgSrc + "\" alt=\"" + imgAlt + "\" />");
					
					items.add(item);
				}
			}
		} catch (ParseException e) {
			throw new FeedDownloadingException(e, URL);
		} catch (MalformedURLException e) {
			throw new FeedDownloadingException(e, URL);
		} catch (IOException e) {
			throw new FeedDownloadingException(e, URL);
		}
		return items;
	}
	
	
	private static Date parseDate(String string, String locale) throws ParseException {
		Matcher matcher = Pattern.compile(PATTERN_DATE).matcher(string);
		
		if(!matcher.find())
			throw new ParseException(string, 0);
		
		SimpleDateFormat formatter = new SimpleDateFormat("d M y H:m");
		
		String dateString = matcher.group();
		
		matcher = Pattern.compile(PATTERN_FIELD_DAY).matcher(dateString);
		if(!matcher.find())
			throw new ParseException("", 0);
		String fieldDay = matcher.group();
		
		matcher = Pattern.compile(PATTERN_FIELD_MONTH).matcher(dateString);
		if(!matcher.find())
			throw new ParseException("", 0);
		String findString = matcher.group();
		Map<String, Integer> months = formatter.getCalendar().getDisplayNames(
			Calendar.MONTH, Calendar.LONG, new Locale(locale));
		String fieldMonth = "1";
		for(String month: months.keySet()) {
			String s1 = month.substring(0, 3);
			String s2 = findString.substring(0, 3);    		
			if(s1.equalsIgnoreCase(s2)) {
				fieldMonth = "" + (months.get(month) + 1);
				break;
			}
		}
		
		matcher = Pattern.compile(PATTERN_FIELD_YEAR).matcher(dateString);
		if(!matcher.find())
			throw new ParseException("", 0);
		String fieldYear = matcher.group();
		
		matcher = Pattern.compile(PATTERN_FIELD_TIME).matcher(dateString);
		if(!matcher.find())
			throw new ParseException("", 0);
		String fieldTime = matcher.group();
		
		return formatter.parse(fieldDay + " " + fieldMonth + " " + fieldYear + " " + fieldTime);
	}
}
