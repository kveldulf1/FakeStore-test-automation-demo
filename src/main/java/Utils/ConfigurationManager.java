package Utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationManager {
    private static ConfigurationManager instance;

    private String hubUrl;
    private String baseUrl;
    private String browser;
    private final String configurationLocation = "src/configs/Configuration.properties";

    private ConfigurationManager() {
        loadData();

    }

    public static ConfigurationManager getInstance() {
        if (instance == null) {
            instance = new ConfigurationManager();
        }

        return instance;
    }

    public void loadData() {

        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(getConfigurationLocation()));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("There is something wrong with the configuration file. Does it exist?");
        }

        hubUrl = properties.getProperty("hubUrl");
        baseUrl = properties.getProperty("baseUrl");
        browser = properties.getProperty("browser");

    }

    public String getBrowser() {
        return browser;
    }

    public String getHubUrl() {
        return hubUrl;
    }

    public String getConfigurationLocation(){
        return configurationLocation;

    }

    public String getBaseUrl() {
        return baseUrl;
    }
}
