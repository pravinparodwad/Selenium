package com.framework;

public class OSUtils {

    /**
     * Checks if the current operating system is Windows.
     *
     * @return true if the operating system is Windows; false otherwise.
     */
    public static boolean isWinOS() {
        // Retrieve the operating system name from system properties
        String osName = System.getProperty("os.name").toLowerCase();

        // Determine if the OS name contains "win", indicating a Windows operating system
        return osName.contains("win");
    }

    /**
     * Modify the given path for Windows by appending ".exe".
     * This is required for executable files on Windows.
     *
     * @param path the path to be modified
     * @return the modified path if the operating system is Windows; the original path otherwise.
     */
    public static String modifyForWindows(String path) {
        if (isWinOS()) {
            // Append ".exe" for Windows
            return path + ".exe";
        }
        return path;
    }

}
