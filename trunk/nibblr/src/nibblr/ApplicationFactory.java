package nibblr;

public class ApplicationFactory {

	private static Application instance = new Nibblr();

	public static Application getInstance() {
		return instance;
	}

	/**
	 * For testing purposes only, do not use in production code.
	 */
	static void setInstance(Application application) {
		instance = application;
	}
}
