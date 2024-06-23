package xClients.helper;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class configHelper {
    private static final String CONFIG_FILE = "src/main/resources/config/config.properties";

    private static final Properties properties = new Properties();

    static {
        try {
            properties.load(new FileInputStream(CONFIG_FILE));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getUrl() {
        String url = properties.getProperty("baseUrl");
        return url;
    }
    public static String getLogin() {
        String login = properties.getProperty("login");
        return login;
    }
    public static String getPassword() {
        String password = properties.getProperty("password");
        return password;
    }
}
