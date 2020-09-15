package PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class OrderReceivedPage extends BasePage {
    WebDriverWait wait;

    @FindBy(css = ".woocommerce-thankyou-order-received")
    private WebElement orderStatusField;

    @FindBy(css = ".woocommerce-thankyou-order-received")
    private List<WebElement> orderStatusFields;

    @FindBy(css = "li.my-account.menu-item-201")
    private WebElement myAccountButton;

    @FindBy(css = "p.woocommerce-notice")
    private WebElement orderStatusInfo;

    public OrderReceivedPage(WebDriver driver) {
        super(driver);
        wait = new WebDriverWait(driver, 5);
    }

    public boolean isOrderSuccessful() {

        int numberOfSuccessMessages = orderStatusFields.size();
        if (numberOfSuccessMessages == 1) {
            return true;
        } else if (numberOfSuccessMessages == 0) {
            return false;
        } else {
            throw new IllegalArgumentException("Number of success messages is " + numberOfSuccessMessages);
        }
    }

    public MyAccountPage goToMyAccount() {

        wait.until(ExpectedConditions.elementToBeClickable(myAccountButton)).click();
        return new MyAccountPage(driver);
    }

    public String getOrderStatus() {

        return wait.until(ExpectedConditions.visibilityOf(orderStatusInfo)).getText();
    }
}
