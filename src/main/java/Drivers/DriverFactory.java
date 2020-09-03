package Drivers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class DriverFactory {


    public WebDriver create(Browser browserType, String hubUrl) throws MalformedURLException {
        switch (browserType) {
            case CHROME:
                return getChromeDriver(hubUrl);
            case FIREFOX:
                return getFireFoxDriver(hubUrl);
            default:
                throw new IllegalArgumentException("Provided browser does not exist.");
        }
    }

    private WebDriver getFireFoxDriver(String hubUrl) throws MalformedURLException {

        FirefoxOptions options = new FirefoxOptions();
        return new RemoteWebDriver(new URL(hubUrl), options);

    }

    private WebDriver getChromeDriver(String hubUrl) throws MalformedURLException {

        ChromeOptions options = new ChromeOptions();
        options.setCapability(CapabilityType.VERSION, "84");
        return new RemoteWebDriver(new URL(hubUrl), options);

    }
}
