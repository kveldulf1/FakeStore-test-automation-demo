package PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DemoFooterPage extends BasePage {

    protected DemoFooterPage(WebDriver driver) {

        super(driver);
    }

    @FindBy(css = ".woocommerce-store-notice__dismiss-link")
    private WebElement demoNoticeDismissButton;

    public void close() {

        demoNoticeDismissButton.click();
    }
}
