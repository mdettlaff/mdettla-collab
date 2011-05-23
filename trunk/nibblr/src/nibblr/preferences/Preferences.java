package nibblr.preferences;

import java.io.File;

public class Preferences {
	
	private static String separator = setSeparator();

	private static File appDir = setAppDir();
	
	private static File dataDir = setDataDir();
	
	private static File iconsDir = setIconsDir();
	
	public static String getSeparator() {
		return separator;
	}

	public static File getAppDir() {
		return appDir;
	}
	
	public static File getDataDir() {
		return dataDir;
	}
	
	public static File getIconsDir() {
		return iconsDir;
	}
	
	// private
	
	private static String setSeparator() {
		return File.separator;
	}
	
	private static File setAppDir() {
		return new File(System.getProperty("user.dir"));
	}
	
	private static File setDataDir() {
		return new File(appDir + separator + "data");
	}
	
	private static File setIconsDir() {
		return new File(dataDir + separator + "icons");
	}
}
