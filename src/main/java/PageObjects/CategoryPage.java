package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class CategoryPage extends BasePage {

    public HeaderPage header;
    public DemoFooterPage demoNotice;
    private WebDriverWait wait;

    @FindBy(css = ".added_to_cart")
    private WebElement viewCartButton;

    @FindBy(css = "a.add_to_cart_button")
    private WebElement addToCartButton;

    @FindBy(css = "a.add_to_cart_button")
    private List<WebElement> addToCartButtons;

    @FindBy(css = "a[title='Zobacz koszyk']")
    private WebElement categoryViewCartButton;

    private By categoryViewCartButtonLocator = By.cssSelector("a[title='Zobacz koszyk']");


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

        wait.until(ExpectedConditions.elementToBeClickable(addToCartButton)).click();
        return this;
    }

    public CartPage viewCart() {

        wait.until(ExpectedConditions.elementToBeClickable(viewCartButton)).click();
        return new CartPage(driver);
    }

    public CategoryPage addAllProductsToCart() {

        wait.until(ExpectedConditions.visibilityOf(addToCartButton));
        List<WebElement> productsAddToCartButtons = driver.findElements(By.cssSelector("a.add_to_cart_button"));

        int i = 0;
        for (WebElement singleAddToCartButton : productsAddToCartButtons) {
            singleAddToCartButton.click();
            wait.until(ExpectedConditions.numberOfElementsToBe(categoryViewCartButtonLocator, ++i));
        }
        return this;
    }
}
