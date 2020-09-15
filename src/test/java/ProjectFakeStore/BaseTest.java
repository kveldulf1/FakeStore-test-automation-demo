package ProjectFakeStore;

import Drivers.DriverFactory;
import Helpers.TestStatus;
import Utils.ConfigurationReader;
import Utils.TestDataReader;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

@TestInstance(TestInstance.Lifecycle.PER_CLASS) // a new test instance of a test class will be created once per class

public class BaseTest {

    protected WebDriver driver;
    protected ConfigurationReader configuration;
    protected TestDataReader testData;
    private String testDataLocation = "src/test/java/TestData.properties";
    private String configurationLocation = "src/configs/Configuration.properties";

    @RegisterExtension
    TestStatus status = new TestStatus();

    @BeforeAll
    public void getConfiguration() {

        configuration = new ConfigurationReader(configurationLocation);
        testData = new TestDataReader(testDataLocation);
    }

    @BeforeEach
    public void testSetUp() {

        DriverFactory driverFactory = new DriverFactory();   // Run starthub.bat and startnode.bat before tests execution
        driver = driverFactory.create(configuration);
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
    }

    @AfterEach
    public void driverQuit(TestInfo info) throws IOException {
        if (status.isFailed) {
            System.out.println("Test screenshot is available at: " + takeScreenshot(info));
        }
        driver.quit();
    }

    private String takeScreenshot(TestInfo info) throws IOException {
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        LocalDateTime timeNow = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss");
        String path = "C:\\Users\\mkownacki1\\Documents\\Testelka\\screenshots\\" + info.getDisplayName() + " " + formatter.format(timeNow) + ".png";
        FileHandler.copy(screenshot, new File(path));
        return path;
    }
}