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
        return properties.getProperty("baseUrl");
    }

    public static String getLogin() {
        return properties.getProperty("login");
    }

    public static String getPassword() {
        return properties.getProperty("password");
    }

    public static String getAuthUrl() {
        return properties.getProperty("authUrl");
    }

    public static String getCompanyUrl() {
        return properties.getProperty("companyUrl");
    }

    public static String getDeleteCompanyUrl() {
        return properties.getProperty("deleteCompanyUrl");
    }

    public static String getEmployeeUrl() {
        return properties.getProperty("employeeUrl");
    }

    public static String getUserDb() {
        return properties.getProperty("userDb");
    }

    public static String getPasswordDb() {
        return properties.getProperty("passwordDb");
    }

    public static String getConnectionString() {
        return properties.getProperty("connectionString");
    }

    public static String getCompanyById() {
        return properties.getProperty("getCompanyById");
    }
}
