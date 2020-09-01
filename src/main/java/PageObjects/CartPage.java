package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartPage extends BasePage {

    private WebDriverWait wait;
    private By productQuantityFieldLocator = By.cssSelector("div.quantity>input");
    private By productsInCartLocator = By.cssSelector("tr.cart_item");
    private By shopTableLocator = By.cssSelector("form>.shop_table");
    private By updateCartButtonLocator = By.cssSelector("button[name='update_cart']");
    private By loadingIconLocator = By.cssSelector(".blockOverlay");
    private By removeProductFromCartButton = By.cssSelector("a.remove");
    private By emptyCartMessageContainerLocator = By.cssSelector("p.cart-empty");

    public CartPage(WebDriver driver) {

        super(driver);
        wait = new WebDriverWait(driver, 5);

    }

    public int getProductQuantity() {

        String productsQuantity = wait.until(ExpectedConditions.presenceOfElementLocated(productQuantityFieldLocator)).getAttribute("value");
        int quantity = Integer.parseInt(productsQuantity);

        return quantity;
    }

    public String getQuantityValue() {

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.quantity>input")));
        WebElement quantityInputAfterRefresh = driver.findElement(productQuantityFieldLocator);
        By quantityInputAfterRefreshLocator = By.cssSelector("div.quantity>input");
        wait.until(ExpectedConditions.presenceOfElementLocated(quantityInputAfterRefreshLocator));
        return quantityInputAfterRefresh.getAttribute("value");

    }

    public int getNumberOfProducts() {

        wait = new WebDriverWait(driver, 7);
        wait.until(ExpectedConditions.presenceOfElementLocated(shopTableLocator));
        return driver.findElements(productsInCartLocator).size();

    }

    public CartPage setQuantityValue(int quantity) {

        wait.until(ExpectedConditions.elementToBeClickable(productQuantityFieldLocator));
        WebElement quantityInput = driver.findElement(productQuantityFieldLocator);

        quantityInput.clear();
        quantityInput.sendKeys(Integer.toString(quantity));
        return new CartPage(driver);
    }

    public CartPage updateCart() {

        wait = new WebDriverWait(driver, 3);
        wait.until(ExpectedConditions.elementToBeClickable(updateCartButtonLocator));
        driver.findElement(updateCartButtonLocator).click();

        return new CartPage(driver);
    }

    public CartPage waitForProccesingEnd() {

        wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.numberOfElementsToBe(loadingIconLocator, 0));

        return new CartPage(driver);
    }

    public CartPage removeProduct() {

        wait.until(ExpectedConditions.elementToBeClickable(removeProductFromCartButton)).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(emptyCartMessageContainerLocator)).getText();

        return new CartPage(driver);

    }

    public boolean isCartEmpty() {

        int shopTableElements = driver.findElements(shopTableLocator).size();
        if (shopTableElements == 1) {
            return false;
        } else if (shopTableElements == 0) {
            return true;
        } else {
            throw new IllegalArgumentException("Wrong number of shop table elements: there can be only one or none.");
        }
    }


}


