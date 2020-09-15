package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MyAccountPage extends BasePage {

    WebDriverWait wait;

    private By myOrdersButtonLocator = By.cssSelector("li.woocommerce-MyAccount-navigation-link--orders>a");
    private By deleteAccountButtonLocator = By.cssSelector("a.delete-me");

    public MyAccountPage(WebDriver driver) {

        super(driver);
        wait = new WebDriverWait(driver, 10);
    }

    public MyOrdersPage goToMyOrders() {

        wait.until(ExpectedConditions.elementToBeClickable(myOrdersButtonLocator)).click();
        return new MyOrdersPage(driver);
    }

    public void deleteAccount() {

        wait.until(ExpectedConditions.presenceOfElementLocated(deleteAccountButtonLocator)).click();
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
        wait.until(ExpectedConditions.urlToBe("https://fakestore.testelka.pl/"));
    }
}
