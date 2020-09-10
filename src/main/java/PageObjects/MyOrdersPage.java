package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MyOrdersPage extends BasePage {

    WebDriverWait wait = new WebDriverWait(driver, 5);

    private By placedOrderRows = By.cssSelector("div.woocommerce-MyAccount-content>table>tbody");
    private By myAccountButton = By.cssSelector("li.my-account.menu-item-201");

    public MyOrdersPage(WebDriver driver) {
        super(driver);

    }

    public int getNumberOfRows() {

        return driver.findElements(placedOrderRows).size();
    }

    public MyAccountPage goToMyAccount() {

        wait.until(ExpectedConditions.elementToBeClickable(myAccountButton)).click();

        return new MyAccountPage(driver);

    }
}
