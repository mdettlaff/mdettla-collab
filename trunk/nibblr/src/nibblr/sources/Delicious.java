package nibblr.sources;

import java.io.Reader;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import nibblr.domain.Feed;
import nibblr.domain.FeedItem;
import nibblr.http.HttpRequest;
import nibblr.http.HttpRequestFactory;

public class Delicious implements FeedSource {

	private static final String URL = "http://delicious.com/";

	private final HttpRequestFactory requestFactory;

	public Delicious(HttpRequestFactory requestFactory) {
		this.requestFactory = requestFactory;
	}

	@Override
	public Feed downloadFeedInfo() {
		Feed feed = new Feed();
		feed.setName("Delicious");
		feed.setDescription("Bookmarking service");
		feed.setUrl(URL);
		return feed;
	}

	@Override
	public Feed downloadFeedWithItems() {
		Feed feed = downloadFeedInfo();
		feed.setItems(downloadFeedItems());
		return feed;
	}

	private List<FeedItem> downloadFeedItems() {
		List<FeedItem> items;
		try {
			String xmlResponse = getXMLResponse();
			items = parseXMLResponse(xmlResponse);
		} catch (XMLStreamException e) {
			throw new FeedDownloadingException(e, URL);
		} catch (ParseException e) {
			throw new FeedDownloadingException(e, URL);
		}
		return items;
	}

	private String getXMLResponse() {
		String url = "https://api.del.icio.us/v1/posts/get";
		HttpRequest request = requestFactory.createRequest(url);
		String xmlResponse = request.doGet();
		return xmlResponse;
	}

	private List<FeedItem> parseXMLResponse(String xmlResponse)
	throws XMLStreamException, ParseException {
		Reader reader = new StringReader(xmlResponse);
		List<FeedItem> items = new ArrayList<FeedItem>();
		XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		XMLStreamReader xmlReader = inputFactory.createXMLStreamReader(reader);
		while (xmlReader.hasNext()) {
			switch (xmlReader.next()) {
			case XMLStreamConstants.START_ELEMENT:
				if ("post".equals(xmlReader.getLocalName())) {
					FeedItem item = parseDeliciousPost(xmlReader);
					items.add(item);
				}
			}
		}
		return items;
	}

	private FeedItem parseDeliciousPost(XMLStreamReader xmlReader)
	throws ParseException {
		FeedItem item = new FeedItem();
		item.setUrl(xmlReader.getAttributeValue(null, "href"));
		item.setTitle(xmlReader.getAttributeValue(null, "description"));
		item.setHTMLContent(xmlReader.getAttributeValue(null, "extended"));
		String date = xmlReader.getAttributeValue(null, "time");
		item.setDate(parseDate(date));
		return item;
	}

	static Date parseDate(String dateToParse) throws ParseException {
		final String FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
		DateFormat dateFormat = new SimpleDateFormat(FORMAT);
		return dateFormat.parse(dateToParse);
	}
}
