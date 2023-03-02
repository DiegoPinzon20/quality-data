package co.com.quality.data.utilities;

import java.util.Map;

public class SystemPropertyUtilities {

    private static final String KEY_USER_DIR = "user.dir";
    private static final String KEY_USER_HOME = "user.home";
    private static final String KEY_OS_NAME = "os.name";
    private static final String KEY_USERNAME = "user.name";
    private static final String KEY_LINE_SEPARATOR = "line.separator";
    private static final String KEY_FILE_SEPARATOR = "file.separator";
    private static final String KEY_COMPUTER_NAME = "COMPUTERNAME";
    private static final String KEY_HOSTNAME = "HOSTNAME";
    private static final String MSG_COMPUTER_NAME_NOT_FOUND = "Unknown Computer";

    private SystemPropertyUtilities() {
    }

    public static String getUserDir() {
        return System.getProperty(KEY_USER_DIR);
    }

    public static String getUserHome() {
        return System.getProperty(KEY_USER_HOME);
    }

    public static String getOsName() {
        return System.getProperty(KEY_OS_NAME);
    }

    public static String getUsername() {
        return System.getProperty(KEY_USERNAME);
    }

    public static String getLineSeparator() {
        return System.getProperty(KEY_LINE_SEPARATOR);
    }

    public static String getFileSeparator() {
        return System.getProperty(KEY_FILE_SEPARATOR);
    }

    public static String getServerName() {
        Map<String, String> env = System.getenv();
        if (env.containsKey(KEY_COMPUTER_NAME))
            return env.get(KEY_COMPUTER_NAME);
        else return env.getOrDefault(KEY_HOSTNAME, MSG_COMPUTER_NAME_NOT_FOUND);
    }

}
