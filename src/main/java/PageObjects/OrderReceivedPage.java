package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class OrderReceivedPage extends BasePage {
    WebDriverWait wait;

    private By orderStatusLocator = By.cssSelector(".woocommerce-thankyou-order-received");


    public OrderReceivedPage(WebDriver driver) {
        super(driver);
        wait = new WebDriverWait(driver, 5);

    }

    public boolean isOrderSuccessful() {

        int numberOfSuccessMessages = driver.findElements(orderStatusLocator).size();
        if (numberOfSuccessMessages == 1) {
            return true;
        } else if (numberOfSuccessMessages == 0) {
            return false;
        } else {
            throw new IllegalArgumentException("Number of success messages is " + numberOfSuccessMessages);
        }
    }

}
