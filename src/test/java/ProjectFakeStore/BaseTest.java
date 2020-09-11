package ProjectFakeStore;

import Drivers.DriverFactory;
import Utils.ConfigurationManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

public  class BaseTest {

    protected WebDriver driver;
    protected String baseUrl = ConfigurationManager.getInstance().getBaseUrl();


    @BeforeEach
    public void testSetUp(){

        DriverFactory driverFactory = new DriverFactory();   // Run starthub.bat and startnode.bat before tests execution
        driver = driverFactory.create();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);

    }

    @AfterEach
    public void driverQuit() { driver.quit();
    }
}