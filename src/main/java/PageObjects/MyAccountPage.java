package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MyAccountPage extends BasePage {

    WebDriverWait wait;

    @FindBy(css = "li.woocommerce-MyAccount-navigation-link--orders>a")
    private WebElement myOrdersButton;

    @FindBy(css = "a.delete-me")
    private WebElement deleteAccountButton;

    public MyAccountPage(WebDriver driver) {

        super(driver);
        wait = new WebDriverWait(driver, 10);
    }

    public MyOrdersPage goToMyOrders() {

        wait.until(ExpectedConditions.elementToBeClickable(myOrdersButton)).click();
        return new MyOrdersPage(driver);
    }

    public void deleteAccount() {

        wait.until(ExpectedConditions.visibilityOf(deleteAccountButton)).click();
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
        wait.until(ExpectedConditions.urlToBe("https://fakestore.testelka.pl/"));
    }
}
