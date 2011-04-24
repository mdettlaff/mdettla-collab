package nibblr;

public class ApplicationFactory {

	private static Application instance = new Nibblr();

	public static Application getInstance() {
		return instance;
	}

	/**
	 * Do celów testowych, nie używać w kodzie produkcyjnym.
	 */
	static void setInstance(Application application) {
		instance = application;
	}
}
