package PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public abstract class BasePage {
    protected WebDriver driver;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
}
