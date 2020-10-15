package com.framework;

public class OSUtils {
	
	public static boolean isWinOS() {
		return System.getProperty("os.name").toLowerCase().contains("win");
	}
	
	public static String modifyForWindows(String path) {
		if (isWinOS()) {
			return path + ".exe";
		}
		
		return path;
	}

}
