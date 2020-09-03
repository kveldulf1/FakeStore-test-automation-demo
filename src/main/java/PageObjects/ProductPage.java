package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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

    private By viewCartButtonLocator = By.cssSelector(".woocommerce-message>.button");
    private By addToCartButtonLocator = By.cssSelector("button[name='add-to-cart']");
    private By productQuantityFieldLocator = By.cssSelector("input.qty");

    public ProductPage goTo(String productUrl) {

        driver.navigate().to(productUrl);
        return new ProductPage(driver);
    }

    public ProductPage addToCart() {

        wait.until(ExpectedConditions.elementToBeClickable(addToCartButtonLocator)).click();
        return this;
    }

    public CartPage viewCart() {

        wait.until(ExpectedConditions.elementToBeClickable(viewCartButtonLocator)).click();
        return new CartPage(driver);

    }

    public ProductPage setQuantityTo(String quantity) {

        wait.until(ExpectedConditions.elementToBeClickable(productQuantityFieldLocator));
        WebElement quantityInput = driver.findElement(productQuantityFieldLocator);

        quantityInput.clear();
        quantityInput.sendKeys(String.valueOf(quantity));

        return this;
    }
}
