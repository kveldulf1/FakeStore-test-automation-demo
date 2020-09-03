package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class CategoryPage extends BasePage {

    public HeaderPage header;
    public DemoFooterPage demoNotice;
    private WebDriverWait wait;

    private By viewCartButtonLocator = By.cssSelector(".added_to_cart");
    private By addToCartButtonLocator = By.cssSelector("a.add_to_cart_button");
    private By viewCartButton = By.cssSelector("a[title='Zobacz koszyk']");

    public CategoryPage(WebDriver driver) {
        super(driver);
        header = new HeaderPage(driver);
        demoNotice = new DemoFooterPage(driver);
        wait = new WebDriverWait(driver, 5);

    }

    public CategoryPage goTo(String url) {
        driver.navigate().to(url);
        return this;

    }

    public CategoryPage addToCart() {

        wait.until(ExpectedConditions.elementToBeClickable(addToCartButtonLocator)).click();
        return this;

    }

    public CartPage viewCart() {

        wait.until(ExpectedConditions.elementToBeClickable(viewCartButtonLocator)).click();
        return new CartPage(driver);
    }

    public CategoryPage addAllProductsToCart() {

        wait.until(ExpectedConditions.presenceOfElementLocated(addToCartButtonLocator));
        List<WebElement> productsAddToCartButtons = driver.findElements(addToCartButtonLocator);

        int i = 0;
        for (WebElement singleAddToCartButton : productsAddToCartButtons) {
            singleAddToCartButton.click();
            wait.until(ExpectedConditions.numberOfElementsToBe(viewCartButton, ++i));
        }
        return this;
    }
}
