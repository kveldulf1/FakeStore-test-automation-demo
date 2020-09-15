package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HeaderPage extends BasePage {
    protected HeaderPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = ".cart-contents")
    private WebElement totalPriceInformation;

    public CartPage viewCart() {
        totalPriceInformation.click();
        return new CartPage(driver);
    }
}