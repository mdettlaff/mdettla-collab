package nibblr.preferences;

import java.io.File;

public class Preferences {

	private static File appDir = setAppDir();

	public static File getDir() {
		return appDir;
	}
	
	// private
	
	private static File setAppDir() {
		return new File(System.getProperty("user.dir"));
	}
}
