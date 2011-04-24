package nibblr.http;

/**
 * Making a factory of a factory might seem a bit ridiculous, but
 * {@link HttpRequestFactory} cannot be injected into a JADE agent,
 * hence the need for another factory.
 */
public class HttpRequestFactoryFactory {

	private static HttpRequestFactory instance = new HttpRequestFactoryImpl();

	public static HttpRequestFactory getInstance() {
		return instance;
	}

	/**
	 * For testing purposes only, do not use in production code.
	 */
	public static void setInstance(HttpRequestFactory factory) {
		instance = factory;
	}
}
