package PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class MyOrdersPage extends BasePage {

    WebDriverWait wait = new WebDriverWait(driver, 5);

    @FindBy(css = "div.woocommerce-MyAccount-content>table>tbody")
    private List<WebElement> placedOrderRows;

    @FindBy(css = "li.my-account.menu-item-201")
    private WebElement myAccountButton;

    public MyOrdersPage(WebDriver driver) {
        super(driver);
    }

    public int getNumberOfRows() {

        return placedOrderRows.size();
    }

    public MyAccountPage goToMyAccount() {

        wait.until(ExpectedConditions.elementToBeClickable(myAccountButton)).click();
        return new MyAccountPage(driver);
    }
}
