package PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductPage extends BasePage {

    private WebDriverWait wait;

    public DemoFooterPage demoNotice;

    public ProductPage(WebDriver driver) {

        super(driver);
        wait = new WebDriverWait(driver, 3);
        demoNotice = new DemoFooterPage(driver);
    }

    @FindBy(css = ".woocommerce-message>.button")
    private WebElement viewCartButton;

    @FindBy(css = "button[name='add-to-cart']")
    private WebElement addToCartButton;

    @FindBy(css = "div.quantity>input")
    private WebElement productQuantityField;

    public ProductPage goTo(String url) {
        driver.navigate().to(url);
        return this;
    }

    public ProductPage addToCart() {

        wait.until(ExpectedConditions.elementToBeClickable(addToCartButton)).click();
        return this;
    }

    public CartPage viewCart() {

        wait.until(ExpectedConditions.elementToBeClickable(viewCartButton)).click();
        return new CartPage(driver);
    }

    public ProductPage setQuantity(int quantity) {

        wait.until(ExpectedConditions.elementToBeClickable(productQuantityField));
        productQuantityField.clear();
        productQuantityField.sendKeys(Integer.toString(quantity));
        return this;
    }
}
